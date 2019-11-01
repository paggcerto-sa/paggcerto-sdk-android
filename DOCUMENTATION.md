# Documentação

## Pagamento com Pinpad

A SDK da Pagcerto disponibiliza uma série de métodos para auxiliar o desenvolvedor a realizar pagamentos utilizando um pinpad.

Para fazer pagamentos utilizando o pinpad é necessário realizar um conjunto de passos descritos no fluxograma abaixo:

<p align="center">
  <img width="460" height="300" src="https://i.imgur.com/xm5xKZk.png">
</p>

Todo o fluxo de comunicação com o pinpad gira em torno da classe ```PinpadService```. 
Você tem acesso a uma instância dessa classe em ```PagcertoSDK.pinpadService```. Lembrando que para usar essa instância você precisa habilitá-la primeiro. O exempo para fazer isso é mostrado [aqui](https://github.com/paggcerto-sa/paggcerto-sdk-android#exemplo).

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
getMobileDevice(context: Context) | Retorna as informações do aparelho celular necessárias para comunicação com a API | MobileDevice
getPaymentDevice() | Retorna as informações do pinpad necessárias para comunicação com a API | PaymentDevice
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
          <li><code>card</code>: Objeto <code>PayCard</code> com as informações necessárias do cartão para a API processar uma venda com pinpad;</li>
          <li><code>online</code>: Caso essa variável retorne true, é necessário continuar o processo como uma venda digitada e inserir o código de segurança do cartão em <code>securityCode</code> no objeto <code>PayCard</code> descrito acima. Caso retorne false o cartão está pronto para ser processado pela API como uma venda com pinpad.</li>
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
@Test
fun cardTransaction(){
    if(PagcertoSDK.isEnablePinpadService()){
        val pinpadService = PagcertoSDK.pinpadService
        try{
            pinpadService.loadDevices()
            pinpadService.device = pinpadService.listDevice[0]
            if(pinpadService.connect()){
                val activity = context as AppCompatActivity
                val credit = true
                val value = 100.0
                val instalment = 3

                val readCardInterface = object : ReadCardInterface{
                    override fun didReadCard() {/*TASK*/}
                }

                val pinpadServiceCallBack = object : PinpadServiceCallBack{
                    override fun onSuccess(card: PayCard, online: Boolean) {/*TASK*/}
                    override fun onError(message: String) {/*TASK*/}
                }

                pinpadService.getCard(
                    activity,
                    credit,
                    value, 
                    instalment, 
                    readCardInterface, 
                    pinpadServiceCallBack
                )
            }else{
                println("Pinpad connection failed.")
            }
        }catch (ex: Exception){
            println("Bluetooth disabled.")
        }
    }else{
        println("PinpadService disabled.")
    }
}
```
## Rest API
Além da gestão de pinpads, o SDK da Pagcerto oferece suporte a comunicação com diversas API's da Pagcerto:
- Account
- Billing
- Payment
- Payment Account
- Recurring

Todas as requisições feitas nas API's são realizadas de forma assíncrona, por isso é necessário instanciar a interface ```PagcertoCallBack<T>``` sempre que for trabalhar com eles.
Vale ressaltar que só é possível realizar solicitações nas API's caso o usuário tenha especificado o ambiente de desenvolvimento e token em ```PagcertoSDK```.

## Account API

Essa seção irá abordar os métodos do SDK disponíveis na API de account. A classe ```AccountNetwork``` é responsável por disponibilizar estes métodos.

### Criar conta
```createAccount(applicationId: String, createAccountRequest: CreateAccountRequest, callBack: PagcertoCallBack<CreateAccountResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/conta-titular)
### Autenticar com credenciais
```signin(applicationId: String, loginForm: LoginForm, callBack: PagcertoCallBack<Token>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/autenticar-com-credenciais)
### Autenticar com hash
```authHash(applicationId: String, hash: String, callBack: PagcertoCallBack<Token>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/autenticar-com-hash)
### Criar nova senha
```createNewPassword(password: String, callBack: PagcertoCallBack<Token>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/criar-nova-senha)
### Alterar senha
```updatePassword(oldPassword: String, newPassword: String, callBack: PagcertoCallBack<Token>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/alterar-senha)
### Recuperar senha
```sendRecoveryEmail(applicationId: String, recoveryEmail: RecoveryEmail, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/recuperar-senha)
### Consultar bancos
```banks(callBack: PagcertoCallBack<BanksList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/consultar-bancos)
### Consultar tipo de empresa
```businessType(callBack: PagcertoCallBack<BusinessTypesList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/consultar-tipo-de-empresa)
### Consultar cidades
```cities(uf: String, callBack: PagcertoCallBack<CitiesList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/consultar-cidades)
### Consultar ramo de atividade
```businessActivities(callBack: PagcertoCallBack<BusinessActivitiesList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/consultar-ramo-de-atividade)
### Consultar medias de marketing
```marketingMedias(callBack: PagcertoCallBack<MarketingMediasList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/consultar-media-de-marketing)

### Identificar usuário
```identify(callBack: PagcertoCallBack<UserWhoAmI>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/identificar-usuario)
### Obter configurações da conta
```presets(callBack: PagcertoCallBack<PresetsResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/obter-configuracoes)
### Alterar plano dos titulares do parceiro
```updatePresets(presetsRequest: PresetsRequest, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/alterar-plano-dos-titulares-do-parceiro)
### Enviar Selfie
```sendSelfie(documentSelfie: Bitmap, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/enviar-selfie)
### Anexar frente do documento
```sendDocumentFront(documentFront: Bitmap, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/anexar-frente-do-documento)
### Anexar verso do documento
```sendDocumentBack(documentBack: Bitmap, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/anexar-verso-do-documento)
### Criar perfil
```createProfile(name: String, callBack: PagcertoCallBack<ProfileResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/criar-perfil)
### Atualizar perfil
```updateProfile(idProfile: String, profileRequest: ProfileRequest, callBack: PagcertoCallBack<ProfileResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/atualizar-perfil)
### Listar perfis
```listProfiles(filterProfile: FilterProfile, callBack: PagcertoCallBack<ProfilesResponseList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/listar-perfis)
### Pesquisar perfil
```searchProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/pesquisar-perfil)
### Desativar perfil
```disableProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/desativar-perfil)
### Ativar perfil
```enableProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/ativar-perfil)
### Remover perfil
```enableProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/remover-perfil)
### Conceder permissões
```grantPermission(roleId: String, scopesList: ScopesList, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/conceder-permissoes)
### Revogar permissões
```revokePermission(roleId: String, scopesList: ScopesList, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/revogar-permissoes)
### Alterar plano dos titulares do parceiro
```updatePartnerClients(partnerClientRequest: PartnerClientRequest, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/alterar-plano-dos-titulares-do-parceiro)
### Listar titular do parceiro
```getPartnerClients(filterProfile: FilterProfile, callBack: PagcertoCallBack<SellersList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/account/#operation/listar-titular-do-parceiro)

## Payment API

Essa seção irá abordar todos os métodos da SDK disponíveis para a API de pagamentos. Todos os métodos de pagamentos da SDK estão disponíveis na Classe ```PaymentNetwork```. 

### Consultar bandeiras de cartão
```getBin(callBack: PagcertoCallBack<List<Bin>>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-bandeira-de-cartao)
### Consultar transações e boletos
```transactions(filterStatement: FilterTransactionRecord, callBack: PagcertoCallBack<TransactionResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v3/payments/#operation/consultar-transacoes-e-boletos)
### Detalhes do pagamento
```findPayment(paymentId: String, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhes-pagamento)
### Enviar comprovante
```sendReceipt(nsu: String, sendReceipt: SendReceipt, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/enviar-comprovante)
### Gerar PDF do boleto do pagamento
```pdfBankSlipPayment(paymentId: String, callBack: PagcertoCallBack<ByteArray?>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/gerar-pdf-do-boleto-pagamento)
### Cancelar transação do cartão
```cancelCardTransaction(nsu: String, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cancelar-transacao-cartao)
### Cancelar pagamento
```cancelPayment(paymentId: String, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cancelar-pagamento)
### Efetuar pagamento (Cartão)
```payWithCard(pay: Pay, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/efetuar-pagamento-cartao)
- Esse método é responsável por realizar o pagamento utilizando um cartão de crédito ou débito. 
- O objeto ```Pay``` pode ser construído a partir de uma série de métodos dispostos pelo ```pinpadService```. 
- O método ```getCard()``` em ```pinpadService``` retorna o ```PayCard``` que será setado na lista de cards em ```Pay```.
- Outros objetos como ```PaymentDevice``` e ```MobileDevice``` também podem ser obtidos em ```pinpadService```. 
- O SDK não disponibiliza um método que retorne as coordenadas geográficas do celular, cabe ao desenvolvedor construí-lo.

### Continuar pagamento (Cartão)
```continuePayment(paymentId: String, pay: Pay, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/continuar-pagamento)
### Capturar pagamento
```capturePayment(paymentId: String, capturePayment: CapturePayment, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/capturar-pagamento)
### Efetuar pagamento (Boleto)
```bankSlipPay(bankSlipsPay: BankSlipsPay, callBack: PagcertoCallBack<BankSlipPayment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/efetuar-pagamento-boleto)
### Finalizar pagamento
```finalizePayment(paymentId: String, note: String, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/finalizar-pagamento)
### Cancelar boleto
```cancelBankSlip(number: String, cancellationReason: String, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cancelar-boleto)
### Substituir boleto
```replaceBankSlip(number: String, replaceBankSlips: ReplaceBankSlips, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/substituir-boleto)
### Simular pagamento
```simulate(simulation: Simulation, callBack: PagcertoCallBack<SimulationResult>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/simular-pagamento-cartao)
### Cadastrar cartão
```createCard(card: CardRequest, callBack: PagcertoCallBack<CardResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cadastrar-cartao)
### Listar cartões
```cards(filterCards: FilterCards, callBack: PagcertoCallBack<CardResponseList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/listar-cartoes)
### Pesquisar cartão
```findCard(idCard: String, callBack: PagcertoCallBack<CardResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pesquisar-cartao)
### Remover cartão
```deleteCard(idCard: String, callBack: PagcertoCallBack<CardResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/remover-cartao)
### Cadastrar recebedor
```createSplitter(splitter: SplitterRequest, callBack: PagcertoCallBack<SplitterResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cadastrar-recebedor)
### Atualizar recebedor
```updateSplitter(id: String, splitter: SplitterRequest, callBack: PagcertoCallBack<SplitterResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/atualizar-recebedor)
### Listar recebedores
```splitters(filterSplitter: FilterSplitter, callBack: PagcertoCallBack<SplitterResponseList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/listar-recebedores%20)
### Pesquisar recebedor
```findSplitter(id: String, callBack: PagcertoCallBack<SplitterResponse>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pesquisar-recebedores%20)
### Detalhar lançamento de cartão
```getCardTransactions(nsu: String, installmentNumber: Int, callBack: PagcertoCallBack<CardTransactionLink>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhar-lancamento-de-cartao)
### Detalhar lançamento de boleto
```getBankSlipTransaction(bankSlipNumber: String, callBack: PagcertoCallBack<BankSlipTransactionLink>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhar-lancamento-de-boleto)
### Detalhar lançamento diverso
```getOtherTransaction(id: String, callBack: PagcertoCallBack<OtherTransactionLink>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhar-lancamento-diverso)
### Consultar lançamentos futuros do splitter
```futureTransaction(filterFutureTransaction: FilterFutureTransaction, callBack: PagcertoCallBack<FutureTransaction>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-lancamentos-futuros-do-splitter)
### Detalhar lançamento futuro do splitter
```getFutureTransactionDetail(date: String, filterFutureTransaction: FilterFutureTransaction, callBack: PagcertoCallBack<TransactionsList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhar-lancamento-do-splitter)
### Consultar taxa padrão
```defaultFees(filterFee: FilterFee, callBack: PagcertoCallBack<Fee>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-taxa-padrao)
### Consultar taxas do titular
```fees(callBack: PagcertoCallBack<Fee>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-taxas-do-titular)
### Cadastrar taxas (Boleto)
```registerBankSlipFee(holdersBankSlipList: HoldersBankSlipList, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cadastrar-taxas)
### Atualizar taxas (Boleto)
```updateBankSlipFee(holdersBankSlipList: HoldersBankSlipList, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/atualizar-taxas)
### Remover taxas (Boleto)
```deleteBankSlipFee(holderIdList: HolderIdList, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/remover-taxas)
### Atualizar taxa base (Boleto)
```updateBankSlipFeeBase(amountTax: Double, callBack: PagcertoCallBack<FeeBase>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/atualizar-taxa-base-boleto)
### Cadastrar taxas (Cartão)
```registerCardFee(holderCard: HolderCard, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/cadastrar-taxas)
### Atualizar taxa base (Cartão)
```updateCardFeeBase(brandFees: BrandFees, callBack: PagcertoCallBack<BrandFees>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/atualizar-taxa-base-cartao)
### Consultar comissão
```getCommission(filterCommission: FilterComission, callBack: PagcertoCallBack<Commission>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-comissao)
### Emitir segunda via de boleto
```duplicateBankSlip(sellerId: String, bankSlipId: String, callBack: PagcertoCallBack<Payment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/emiti-segunda-via-de-boleto)
### Gerar PDF de boleto de múltiplos pagamentos
```getBankSlipPdfListZip(filterPdfBankSlip: List<String>, callBack: PagcertoCallBack<ByteArray?>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/gerar-pdf-de-boleto-de-multiplos-pagamentos)
### Gerar PDF de múltiplos boletos
```getBankSlipPdfList(filterPdfBankSlip: List<String>, callBack: PagcertoCallBack<ByteArray?>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/gerar-pdf-de-multiplos-boletos)
### Enviar boleto por e-mail
```sendSingleBankSlipByEmail(bankSlipId: String, email: String, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/enviar-boleto-por-email)
### Enviar carnê por e-mail
```sendMultipleBankSlipByEmail(paymentId: String,  email: String, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/enviar-carne-por-email)
### Detalhes do boleto
```checkoutBankSlipDetail(bankSlipId: String, callBack: PagcertoCallBack<CheckoutBankSlip>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhes-do-boleto)
### Gerar arquivo PDF do boleto
```checkoutBankSlipPdf(bankSlipId: String, callBack: PagcertoCallBack<ByteArray?>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/gerar-arquivo-pdf-do-boleto)
### Detalhes do carnê
```checkoutBankSlipListDetail(paymentId: String, callBack: PagcertoCallBack<CheckoutBankSlipList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhes-do-carne)
### Gerar arquivo PDF do carnê
```checkoutBankSlipListPdf(paymentId: String, callBack: PagcertoCallBack<ByteArray?>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/gerar-arquivo-pdf-do-carne)
### Consultar boletos
```getBankSlips(filterBankSlips: FilterBankSlips, callBack: PagcertoCallBack<BankSlipsList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-boletos)
### Pagamentos por horário
```paymentsPerHourly(callBack: PagcertoCallBack<StatisticList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pagamentos-por-horário)
### Pagamentos por dia da semana
```paymentsPerWeekly(callBack: PagcertoCallBack<StatisticList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pagamentos-por-dia-da-semana)
### Pagamentos por mês
```paymentsPerMonthly(callBack: PagcertoCallBack<StatisticList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pagamentos-por-mes)
### Pagamentos por ano
```paymentsPerYearly(callBack: PagcertoCallBack<StatisticList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pagamentos-por-ano)
### Estatísticas por método de pagamento
```statisticsPaymentMethods(callBack: PagcertoCallBack<StatisticPayment>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/estatistica-por-metodo-de-pagamento)
### Pagamentos com boleto no mês atual
```paymentsPerBankSlipsCurrentMonth(callBack: PagcertoCallBack<BankSlipPercentages>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pagamentos-com-boleto-no-mes-atual)
### Consultar taxa de transferência
```bankTransferFees(callBack: PagcertoCallBack<TransferFee>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-taxa-de-transferencia)
### Histórico de solicitações de antecipações
```historyAnticipation(filterAnticipation: FilterAnticipation, callBack: PagcertoCallBack<AnticipationHistory>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/historico-de-solicitacoes-de-antecipacoes)
### Listar transações antecipáveis
```cardTransactionsAvailable(filterTransaction: FilterTransaction, callBack: PagcertoCallBack<AnticipableTransaction>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/lista-transacoes-antecipaveis)
### Solicitar antecipação
```requestAnticipation(transactionsToAnticipate: TransactionsToAnticipate, callBack: PagcertoCallBack<Anticipation>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/solicitar-antecipacao)
### Listar transações da solicitação
```anticipationTransactions(anticipationId: String, filterTransaction: FilterTransaction, callBack: PagcertoCallBack<AnticipableTransaction>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/listar-transacoes-da-solicitacao)
### Aceitar termo
```acceptTerms(callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/aceitar-termo)
### Pode antecipar?
```canAnticipate(callBack: PagcertoCallBack<CanAnticipate>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/pode-antecipar)
### Solicitar antecipação de todas as transações
```anticipateAllTransactions(callBack: PagcertoCallBack<Anticipation>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/solicitar-antecipacao-de-todas-as-transacoes)
### Solicitação em andamento
```anticipationInProgress(callBack: PagcertoCallBack<Anticipation>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/solicitacao-em-andamento)
### Detalhes da solicitação
```anticipationDetail(anticipationId: String, callBack: PagcertoCallBack<Anticipation>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhes-da-solicitacao)
### Listar transações em andamento
```transactionsInProgress(filterTransaction: FilterTransaction, callBack: PagcertoCallBack<RequestedCardTransactionsList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/listar-transacoes-em-andamento)
### Consultar lançamentos futuros do splitter
```futureTransactionSplitter(filterSplitter: FilterSplitterTransaction, callBack: PagcertoCallBack<FutureTransaction>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/consultar-lancamentos-futuros-do-splitter)
### Detalhar lançamento futuro do splitter
```futureTransactionSplitterDetail(date: String, futureFilterTransaction: FilterSplitterTransaction, callBack: PagcertoCallBack<TransactionsList>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/detalhar-lancamento-do-splitter)
### Simular liquidação de boleto
```simulateBankSlipPay(bankSlipsIdList: BankSlipsIdList, callBack: PagcertoCallBack<Boolean>)``` [Detalhes](https://desenvolvedor.paggcerto.com.br/v2/payments/#operation/simular-liquidacao-de-boleto)

## Métodos de cobrança

Essa seção irá abordar todos os métodos da SDK disponíveis para a API de cobrança.

De forma semelhante a API de pagamentos, os métodos dessa API estarão disponíveis na classe ```BillingNetwork```.
Para utilizar essa classe certifique-se que a SDK foi ativada com o método ```PaggcertoSDK.getInstance().activate()```.

```createBilling(billingRequest: Pagg_BillingRequest, callBack: PaggcertoCallBack<Pagg_BillingResponse>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v1/billing/#operation/gerar-cobranca)

```listBillings(filterBilling: Pagg_FilterBilling, callBack: PaggcertoCallBack<Pagg_BillingsResponse>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v1/billing/#operation/listar-cobrancas)

```getBilling(idBilling: String, callBack: PaggcertoCallBack<Pagg_BillingResponse>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v1/billing/#operation/pesquisar-cobranca)

```cancelBilling(idBilling: String, callBack: PaggcertoCallBack<Boolean>)```
[Detalhes](https://desenvolvedor.paggcerto.com.br/v1/billing/#operation/cancelar-cobranca)
