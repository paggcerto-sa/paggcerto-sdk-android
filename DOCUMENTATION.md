# Documentação

## Pagamento com Pinpad

A SDK da Paggcerto disponibiliza uma série de métodos para auxiliar o desenvolvedor a realizar pagementos utilizando um pinpad.

Para fazer pagamentos utilizando o pinpad é necessário realizar um conjunto de passos descritos no fluxograma abaixo:

<p align="center">
  <img width="460" height="300" src="https://i.imgur.com/xm5xKZk.png">
</p>

Todo o fluxo de comunicação com o pinpad gira em torno da classe ```PinpadService```. 
Uma vez que você instancia a SDK através de ```PaggcertoSDK.getInstance()```, você ganha uma referência a um objeto ```PinpadService```. 
Você irá utilizar esta referência para se comunicar com seu pinpad.

A tabela abaixo lista os métodos dispostos na classe ```PinpadService```

Nome do método | Descrição | Retorno
--- | --- | ---
listDevice: ArrayList\<BluetoothDevice\> | Lista de pinpads descobertos pelo celular | -
device: BluetoothDevice | Pinpad que a SDK irá se comunicar. Você escolhe um dos pinpads na listDevice | -
isPinpad(deviceName: String) | Passando o nome do dispositivo, a SDK irá verificar se esse dispositivo é um pinpad e se pode trabalhar com a SDK | Boolean
loadDevices() | Lista os dispositivos descobertos pelo celular e insere na listDevice todos os pinpads suportado pela SDK | Void *[Caso o bluetooth esteja desligado esse método irá lançar uma exceção]*.
removeDevice(device: BluetoothDevice) | Remove um pinpad da listDevice | Void
clearDevice() | Limpa a listDevice | Void
isConnected() | Verifica se o pinpad está conectado com a SDK | Boolean
connect() | Abre uma conexão socket com o pinpad selecionado | Boolean
disconnect(message: String) | Encerra a conexão socket com o pinpad selecionado. É possível mandar uma mensagem que será exibida no visor do pinpad. | Boolean
writeDisplayMessage(message: String) | Envia uma mensagem ao pinpad para ser exibida em seu visor. | Boolean
getPinpadInformation() | Obtem todas as informações disponíveis pelo pinpad | PinpadDescription
getMobileDevice(context: Context) | Retorna as informações do aparelho celular necessárias para comunicação com a API | Pagg_MobileDevice
getPaymentDevice() | Retorna as informações do pinpad necessárias para comunicação com a API | Pagg_PaymentDevice
getCard(activity: Activity, credit: Boolean, value: Double, installments: Int, interface: ReadCardInterface?, callBack: PinpadServiceCallBack) | Realiza a leitura do cartão utilizando o pinpad. Uma descrição mais aprofundada sobre esse método é mostrada logo abaixo desta tabela. | Void
stopCardProccess() | Interrompe o processo de leitura realizado em getCard() | Void

```getCard(activity: Activity, credit: Boolean, value: Double, installments: Int, interface: ReadCardInterface?, callBack: PinpadServiceCallBack) ```

Esse método é responsável por processar a leitura do cartão, identificar se a bandeira do cartão é suportada pela API, identificar se o cartão aceita leitura com tarja, entre outras funções. 
Mais detalhes sobre pagamento com cartão clique [aqui](https://desenvolvedor.paggcerto.com.br/v2/payments/#tag/pagamento-com-cartao).

A função deste método é retornar para o desenvolvedor as informações do cartão necessárias para a API processar uma venda realizada com o pinpad.

A função de cada um dos parâmetros é descrita a seguir:

<ul>
  <li><code>Activity</code>: Esse parâmetro é passado para auxiliar o parâmetro <code>readCardInterface</code> a exercer sua função.</li>
  <li><code>Credit</code>: Esse parâmetro informa ao método se o cartão utilizado é crédito (true) ou débito (false).</li>
  <li><code>Value</code>: Quantia em dinheiro que o cartão irá processar. O número de parcelas não irá alterar este valor.</li>
  <li><code>Installments</code>: Número de parcelas do pagamento. Se <code>Credit</code> for false o número de parcelas sempre será 1.</li>
  <li><code>ReadCardInterface</code>: Uma interface que auxilia o desenvolvedor a atualizar informações na tela do celular enquanto o processo de leitura do cartão está acontecendo. Essa interface é acionada após o pinpad identificar o cartão inserido. Caso não queira utilizar esse recurso é possível passar <code>null</code> neste parâmetro.</li>
  <li><code>PinpadServiceCallBack</code>: Interface de retorno do processo de leitura. Essa interface possui dois métodos:
    <ul>
      <li><code>onSuccess</code>: Caso o pinpad consiga processar a leitura do cartão, a interface irá acionar esse método.
        <ul>
          <li><code>card</code>: Objeto <code>Pagg_Card</code> com as informações necessárias do cartão para a API processar uma venda com pinpad;</li>
          <li><code>online</code>: Caso essa variável retorne true, é necessário continuar o processo como uma venda digitada e inserir o código de segurança do cartão em <code>securityCode</code> no objeto <code>Pagg_Card</code> descrito acima. Caso retorne false o cartão está pronto para ser processado pela API como uma venda com pinpad.</li>
        </ul>
      </li>
      <li><code>onError</code>: Esse método é acionado quando ocorre uma falha na leitura do cartão. A mensagem descrita no método informa o motivo da falha.</li>
    </ul>
  </li>
</ul>

Se a leitura for feita de forma correta, o ```pinpadServiceCallBack``` irá retornar as informações do cartão necessárias para comunicação com a API. O envio dos dados do cartão será abordado na seção de Métodos de pagamento.

Lembre-se de desconectar o pinpad ao fim do processo.

O trecho de código abaixo mostra como o processo é realizado.

```
final PinpadService pinpadService = Objects.requireNonNull(PaggcertoSDK.Companion.getInstance()).getPinpadService();
try {
	pinpadService.loadDevices();
	pinpadService.setDevice(pinpadService.getListDevice().get(0));

	if(pinpadService.connect()){
		Activity activity = this;
		boolean credit = true;
		double value = 100.0;
		int installments = 3;

		ReadCardInterface readCardInterface = new ReadCardInterface() {
			@Override
			public void didReadCard() {

			}
		};

		PinpadServiceCallBack pinpadServiceCallBack = new PinpadServiceCallBack() {
			@Override
			public void onSuccess(@NotNull Pagg_Card card, boolean online) {
				/*TASK*/

				pinpadService.disconnect("default message");
			}

			@Override
			public void onError(@NotNull String message) {
				/*TASK*/

				pinpadService.disconnect("default message");
			}
		};

		pinpadService.getCard(activity, credit, value, installments, readCardInterface, pinpadServiceCallBack);

	}else {
		Toast.makeText(getApplicationContext(), "Falha ao se conectar com o pinpad.", Toast.LENGTH_LONG).show();
	}

} catch (Exception e) {
	Toast.makeText(getApplicationContext(), "Bluetooth desativado.", Toast.LENGTH_LONG).show();
	e.printStackTrace();
}
```

## Métodos de pagamento

Essa seção irá abordar todos os métodos da SDK disponíveis para a API de pagamentos. 

Todos os métodos de pagamentos da SDK estão disponíveis na Classe ```PaymentNetwork```. 
Eles trabalham de forma assíncrona, por isso é necessário instanciar a interface ```PaggcertoCallBack<T>``` sempre que for trabalhar com eles. 
Para utilizar essa classe certifique-se que a SDK foi ativada com o método ```PaggcertoSDK.getInstance().activate()```.

```payWithCard(pay: Pagg_Pay, callBack: PaggcertoCallBack<Pagg_Payment>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/efetuar-pagamento-cartao)

Esse método é responsável por realizar o pagamento utilizando um cartão de crédito ou débito. 
O objeto ```Pagg_Pay``` pode ser construído a partir de uma série de métodos dispostos pelo ```pinpadService```. 
O método ```getCard()``` em ```PinpadService``` retorna o ```Pagg_Card``` que será setado na lista de cards em ```Pagg_Pay```. 
Outros objetos como ```Pagg_PaymentDevice``` e ```Pagg_MobileDevice``` também podem ser obtidos por lá. 
A SDK não disponibiliza um método que retorne as coordenadas geográficas do celular, cabe ao desenvolvedor construí-lo.

```createCard(card: Pagg_Card, callBack: PaggcertoCallBack<Pagg_Card>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cadastrar-cartao)

```cards(filterCards: Pagg_FilterCards, callBack: PaggcertoCallBack<Pagg_CardList>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/listar-cartoes)

```findCard(idCard: String, callBack: PaggcertoCallBack<Pagg_Card>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pesquisar-cartao)

```deleteCard(idCard: String, callBack: PaggcertoCallBack<Pagg_Card>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/remover-cartao)

```simulate(simulation: Pagg_Simulation, callBack: PaggcertoCallBack<Pagg_SimulationResult>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/simular-pagamento-cartao)

```sendReceipt(nsu: String, sendReceipt: Pagg_SendReceipt, callBack: PaggcertoCallBack<Boolean>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/enviar-comprovante)

```bankSlipPay(bankSlipsPay: Pagg_BankSlipsPay, callBack: PaggcertoCallBack<Pagg_Payments>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/efetuar-pagamento-boleto)

```replaceBankslip(number: String, replaceBankSlips: Pagg_ReplaceBankSlips, callBack: PaggcertoCallBack<Pagg_Payment>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/substituir-boleto)

```pdfBankSlipPayment(paymentId: String, callBack: PaggcertoCallBack<ByteArray?>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/gerar-pdf-do-boleto-pagamento)

```createSplitter(splitter: Pagg_Splitter, callBack: PaggcertoCallBack<Pagg_Splitter>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cadastrar-recebedor)

```updateSplitter(id: String, splitter: Pagg_Splitter, callBack: PaggcertoCallBack<Pagg_Splitter>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/atualizar-recebedor)

```splitters(filterSplitter: Pagg_FilterSplitter, callBack: PaggcertoCallBack<Pagg_SplitterList>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/listar-recebedores%20)

```findSplitter(id: String, callBack: PaggcertoCallBack<Pagg_Splitter>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pesquisar-recebedores%20)

```finalizePayment(paymentId: String, note: String, callBack: PaggcertoCallBack<Pagg_Payment>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/finalizar-pagamento)

```cancelPayment(paymentId: String, callBack: PaggcertoCallBack<Pagg_Payment>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cancelar-pagamento)

```cancelCardTransaction(nsu: String, callBack: PaggcertoCallBack<Pagg_Payment>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cancelar-transacao-cartao)

```cancelBankSlip(number: String, cancellationReason: String, callBack: PaggcertoCallBack<Pagg_Payment>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cancelar-boleto)

```findPayment(paymentId: String, callBack: PaggcertoCallBack<Pagg_Payment>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhes-pagamento)

```payments(filterHistoryPayment: Pagg_FilterHistoryPayment, callBack: PaggcertoCallBack<Pagg_HistoryPayments>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-pagamentos)

```balance(callBack: PaggcertoCallBack<Pagg_Balance>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-saldo-de-repasse)
