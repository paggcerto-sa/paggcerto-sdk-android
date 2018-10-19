# PaggcertoSDK 1.0.0

A PaggcertoSDK foi criada para facilitar a integração de sua aplicação android com a API da Paggcerto. Essa SDK possui os principais métodos da API de pagamentos, além disso, a SDK também disponibiliza diversos métodos de comunicação com os pinpads que a Paggcerto trabalha.

## Pré-Requisitos

Android 5.0 ou superior.
Instancie as seguintes dependencias:
```
implementation 'com.squareup.retrofit2:converter-gson:latest.version'
implementation 'com.squareup.retrofit2:converter-scalars:latest.version'
```

## Integrando a SDK na aplicação

Para utilizar todos os recursos da SDK é necessário fazer alguns ajustes iniciais em sua aplicação.

### Instancie a SDK em sua aplicação

Faça o download da SDK clicando [aqui](https://github.com/paggcerto-sa/paggcerto-sdk-android/raw/master/paggcertosdk-1.0.0.aar) e salve ela em ```.\app\libs```.

No Build.Gradle de sua aplicação instancie a SDK da seguinte forma
```
implementation(name: 'paggcertosdk:latest.version', ext: 'aar')
```
Já no Build.Gradle do projeto adicione
```
repositories {
   flatDir {
       dirs 'libs'
   }
}
```

### Utilizando a SDK em sua aplicação

```PaggcertoSDK.getInstance()```

Carrega uma instancia da SDK com as configurações básicas na aplicação. 
Esse é um método estático que cria um objeto único que poderá ser usado em qualquer lugar da aplicação. 
Ele é responsável por armazenar informações importantes que serão usadas pela API como token, bandeiras e serviços do pinpad.

```activate(token: String, environment: Int, paggcertoSDKResponse: PaggcertoSDKResponse)```

Após carregar uma instância da SDK, é necessário ativá-la para informar seu token e em qual ambiente irá trabalhar. 

No parâmetro ```token``` você irá setar seu token de acesso.
Para saber como conseguir seu token clique [aqui](https://desenvolvedor.paggcerto.com.br/v2/account/).

No parâmetro ```environment``` você irá declarar o ambiente de trabalho. A SDK fornece constantes para acessar esses ambientes:

* ```SANDBOX```: Esse ambiente permite o usuário trabalhar em sandbox.
* ```PRODUCTION```: Esse ambiente permite o usuário trabalhar em produção.

```PaggcertoSDKResponse``` é uma interface de retorno que irá informar se você conseguiu ativar a SDK com sucesso. 
Ao implementar essa interface, um método ```onResult(result: Boolean, message: String)``` é criado. 
Caso o parâmetro ```result``` seja verdadeiro, a conexão foi estabelecida e a SDK foi ativada com sucesso. Uma mensagem de retorno é enviar no campo ```message```.
Lembrando que o token de acesso gerado em sandbox só irá funcionar no ambiente ```SANDBOX```, do mesmo modo um token gerado para o ambiente de produção só irá funcionar no ambiente ```PRODUCTION```.

```isActive(): Boolean```

Retorna verdadeiro caso a SDK tenha sido ativada. 
Como a SDK só precisa ser ativada uma vez na aplicação, esse método auxilia o desenvolvedor a evitar múltiplas ativações.


### Exemplo

Segue um exemplo de como fazer a configuração inicial da SDK

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
