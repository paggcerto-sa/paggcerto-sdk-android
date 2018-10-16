# PaggcertoSDK Beta 0.9

A PaggcertoSDK Beta 0.9 foi criada para facilitar a integração de sua aplicação android com a API da Paggcerto. Essa SDK possui os principais métodos da API de pagamento, além disso, a SDK também disponibiliza diversos métodos de comunicação com os pinpads que a Paggcerto trabalha.

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

Faça o download da SDK clicando [aqui](#) e salve ela em ```.\app\libs```.

No Build.Gradle de sua aplicação instancie a SDK da seguinte forma
```
implementation(name: 'paggcertosdk-beta-0.9', ext: 'aar')
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

```token: Pagg_Token()```

Após carregar uma instância da SDK, é necessário setar seu token de acesso. 
Para saber como conseguir seu token clique [aqui](https://desenvolvedor.paggcerto.com.br/v2/account/).

```activate(environment: String, paggcertoSDKResponse: PaggcertoSDKResponse)```

Com o token setado, agora é hora de ativar a SDK para informar em qual ambiente irá trabalhar. 
No parâmetro ```environment``` você irá declarar qual é esse ambiente. Duas opções estão disponíveis:

* ```sandbox```: Esse ambiente permite o usuário trabalhar em sandbox.
* ```production```: Esse ambiente permite o usuário trabalhar em produção.

```PaggcertoSDKResponse``` é uma interface de retorno que irá informar se você conseguiu ativar a SDK com sucesso. 
Ao implementar essa interface, um método ```onResult(result: Boolean)``` é criado. 
Caso o parâmetro ```result``` seja verdadeiro, a conexão foi estabelecida e a SDK foi ativada com sucesso. 
Lembrando que o token de acesso gerado em sandbox só irá funcionar no ambiente ```sandbox``` do mesmo modo um token gerado para o ambiente de produção só irá funcionar no ambiente ```production```.

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

     Pagg_Token token = new Pagg_Token();
     token.setToken("Meu Token");
     paggcertoSDK.setToken(token);
        
     if(!paggcertoSDK.isActive()){
         paggcertoSDK.activate("sandbox", new PaggcertoSDKResponse() {
             @Override
             public void onResult(boolean result) {
                 print(result? "Conexão estabelecida" : "Falha ao se conectar com API");
             }
         });    
     }
 }
```
