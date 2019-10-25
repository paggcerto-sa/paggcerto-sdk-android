[![](https://jitpack.io/v/paggcerto-sa/paggcerto-sdk-android.svg)](https://jitpack.io/#paggcerto-sa/paggcerto-sdk-android)

# PagcertoSDK
A PagcertoSDK foi criada para facilitar a integração de sua aplicação android com a API da Pagcerto. Esse SDK possui todos os métodos das API's da Pagcerto, além disso, o SDK também disponibiliza diversos métodos de comunicação com os pinpads que a Pagcerto trabalha.

## Pré-Requisitos
Android 5.0 ou superior.

## Integrando o SDK na aplicação
Para utilizar todos os recursos do SDK é necessário fazer alguns ajustes iniciais em sua aplicação.

### Instancie o SDK em sua aplicação
No ```build.bradle``` de sua aplicação instancie o SDK da seguinte forma
```
dependencies {
    implementation 'com.github.paggcerto-sa:paggcerto-sdk-android:latest_version'
}
```
Já no ```build.gradle``` do projeto adicione
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### Utilizando o SDK em sua aplicação

#### PagcertoSDK
PagcertoSDK é um singleton que possui as configurações básicas que serão utilizadas no SDK.
Ele é responsável por armazenar informações importantes que serão usadas pela API como token, ambiente de desenvolvimento e serviços do pinpad.

A primeira coisa a se fazer com o SDK é informar o ambiente de trabalho, para fazer isso basta dizer qual ambiente quer trabalhar em `PagcertoSDK.environment`.
O SDK fornece constantes para acessar esses ambientes:

* ```SANDBOX```: Esse ambiente permite o usuário trabalhar em sandbox.
* ```PRODUCTION```: Esse ambiente permite o usuário trabalhar em produção.

Após dizer qual ambiente irá trabalhar, é necessário informar o token de acesso em `PagcertoSDK.token`. 
Para saber como conseguir seu token clique [aqui](https://desenvolvedor.paggcerto.com.br/v2/account/).

#### PinpadService
PinpadService é o objeto responsável por fazer a gestão entre aplicativo e pinpad. PagcertoSDK possui uma instância desse objeto. Para usar esse recurso você precisa habilitá-lo usando o método `PagcertoSDK.enablePinpadService`. Como se trata de um método assíncrono, é necessário aguardar a resposta através da interface `PagcertoSDKResponse`.
O método `PagcertoSDK.isEnablePinpadService` retorna `true` caso tenha havido sucesso na ativação do PinpadService.

### Exemplo

Segue um exemplo de como fazer a configuração inicial do SDK

```Kotlin
@Test
fun activate(){
    PagcertoSDK.environment = Environment.SANDBOX
    PagcertoSDK.token = "token"

    PagcertoSDK.enablePinpadService(context, object : PagcertoSDKResponse{
        override fun onResult(result: Boolean, message: String) {
            Assert.assertEquals(true, PagcertoSDK.isEnablePinpadService())
        }
    })
}
```
