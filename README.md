# PaggcertoSDK 1.0.1

A PaggcertoSDK foi criada para facilitar a integração de sua aplicação android com a API da Paggcerto. Esse SDK possui os principais métodos da API de pagamentos, além disso, o SDK também disponibiliza diversos métodos de comunicação com os pinpads que a Paggcerto trabalha.

## Pré-Requisitos

Android 5.0 ou superior.
Instancie as seguintes dependências:
```
implementation 'com.squareup.retrofit2:converter-gson:latest.version'
implementation 'com.squareup.retrofit2:converter-scalars:latest.version'
```

## Integrando o SDK na aplicação

Para utilizar todos os recursos do SDK é necessário fazer alguns ajustes iniciais em sua aplicação.

### Instancie o SDK em sua aplicação

Faça o download do SDK clicando [aqui](https://github.com/paggcerto-sa/paggcerto-sdk-android/raw/master/paggcertosdk-1.0.1.aar) e salve ele em ```.\app\libs```.

No ```build.bradle``` de sua aplicação instancie o SDK da seguinte forma
```
implementation(name: 'paggcertosdk-latest_version', ext: 'aar')
```
Já no ```build.gradle``` do projeto adicione
```
repositories {
   flatDir {
       dirs 'libs'
   }
}
```

### Utilizando o SDK em sua aplicação

```PaggcertoSDK.getInstance()```

Carrega uma instancia do SDK com as configurações básicas na aplicação. 
Esse é um método estático que cria um objeto único que poderá ser usado em qualquer lugar da aplicação. 
Ele é responsável por armazenar informações importantes que serão usadas pela API como token, bandeiras e serviços do pinpad.

```activate(token: String, environment: Int, paggcertoSDKResponse: PaggcertoSDKResponse)```

Após carregar uma instância do SDK, é necessário ativá-lo informando seu token e em qual ambiente irá trabalhar. 

No parâmetro ```token``` você irá setar seu token de acesso.
Para saber como conseguir seu token clique [aqui](https://desenvolvedor.paggcerto.com.br/v2/account/).

No parâmetro ```environment``` você irá declarar o ambiente de trabalho. O SDK fornece constantes para acessar esses ambientes:

* ```SANDBOX```: Esse ambiente permite o usuário trabalhar em sandbox.
* ```PRODUCTION```: Esse ambiente permite o usuário trabalhar em produção.

```PaggcertoSDKResponse``` é uma interface de retorno que irá informar se você conseguiu ativar o SDK com sucesso. 
Ao implementar essa interface, um método ```onResult(result: Boolean, message: String)``` é criado. 
Caso o parâmetro ```result``` seja verdadeiro, a conexão foi estabelecida e o SDK foi ativado com sucesso. Uma mensagem de retorno é enviada no campo ```message```.
Lembrando que o token de acesso gerado em sandbox só irá funcionar no ambiente ```SANDBOX```, do mesmo modo um token gerado para o ambiente de produção só irá funcionar no ambiente ```PRODUCTION```.

```isActive(): Boolean```

Retorna verdadeiro caso o SDK tenha sido ativado. 
Como o SDK só preciso ser ativado uma vez na aplicação, esse método auxilia o desenvolvedor a evitar múltiplas ativações.


### Exemplo

Segue um exemplo de como fazer a configuração inicial do SDK

```
 PaggcertoSDK paggcertoSDK = PaggcertoSDK.Companion.getInstance();

 @Override
 protected void onCreate(@Nullable Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

     String token = "Meu Token";

     if(!paggcertoSDK.isActive()){
         paggcertoSDK.activate(token, PaggcertoSDK.SANDBOX, new PaggcertoSDKResponse() {
             @Override
             public void onResult(boolean result, @NonNull String message) {
                 print(message);
             }
         });
     }
  }
```
