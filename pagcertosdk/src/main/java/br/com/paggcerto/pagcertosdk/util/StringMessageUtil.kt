package br.com.paggcerto.pagcertosdk.util

internal object StringMessageUtil {

    const val errorInstallDebit = "Debito não aceita parcelas"
    const val errorLoadTables = "Falha ao carregar as tabelas"
    const val errorDownloadTables = "Falha ao utilizar as tabelas"
    const val errorReadChip = "Falha ao ler chip do cartão"
    const val errorTransaction = " Transação inválida " //"   Transacao       invalida    "
    const val errorGetNumberCard = "Falha ao obter número do cartão"
    const val errorBinNotFound = "Bandeira não encontrada"
    const val errorBinInstall = "Número de parcelas maior que o número permitido pela bandeira"
    const val errorCreateCard = "Falha na contrução do cartão"
    const val errorRequiredChip = "É necessário o uso do chip"
    const val errorReadMagneticStripe = "Ocorreu um erro de leitura na tarja"
    const val errorPinpadCommunication = "Falha ao se comunicar com o pinpad"
    const val errorRequiredMagneticStripe = "Utilize a tarja para esse cartão"
    const val errorTimeOut = "Tempo limite excedido"

    const val transactionCanceled = "Operação cancelada pelo usuário"
}