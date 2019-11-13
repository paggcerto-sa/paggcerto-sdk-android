package br.com.paggcerto.pagcertosdk.rest.payment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.paggcerto.pagcertosdk.PagcertoCallBack;
import br.com.paggcerto.pagcertosdk.model.payment_account.response.Card;
import br.com.paggcerto.pagcertosdk.model.payments.request.AddressRequest;
import br.com.paggcerto.pagcertosdk.model.payments.request.AnticipationFees;
import br.com.paggcerto.pagcertosdk.model.payments.request.BankAccountRequest;
import br.com.paggcerto.pagcertosdk.model.payments.request.BankSlipRequest;
import br.com.paggcerto.pagcertosdk.model.payments.request.BankSlipsIdList;
import br.com.paggcerto.pagcertosdk.model.payments.request.BankSlipsPay;
import br.com.paggcerto.pagcertosdk.model.payments.request.BrandFees;
import br.com.paggcerto.pagcertosdk.model.payments.request.CapturePayment;
import br.com.paggcerto.pagcertosdk.model.payments.request.CapturePaymentSplitter;
import br.com.paggcerto.pagcertosdk.model.payments.request.CardRequest;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterAnticipation;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterBankSlips;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterCards;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterComission;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterFee;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterHistoryPayment;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterSplitter;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterSplitterTransaction;
import br.com.paggcerto.pagcertosdk.model.payments.request.FilterTransaction;
import br.com.paggcerto.pagcertosdk.model.payments.request.HolderBankSlip;
import br.com.paggcerto.pagcertosdk.model.payments.request.HolderCard;
import br.com.paggcerto.pagcertosdk.model.payments.request.HolderIdList;
import br.com.paggcerto.pagcertosdk.model.payments.request.HoldersBankSlipList;
import br.com.paggcerto.pagcertosdk.model.payments.request.Pay;
import br.com.paggcerto.pagcertosdk.model.payments.request.PayCard;
import br.com.paggcerto.pagcertosdk.model.payments.request.Payer;
import br.com.paggcerto.pagcertosdk.model.payments.request.ReplaceBankSlips;
import br.com.paggcerto.pagcertosdk.model.payments.request.SendReceipt;
import br.com.paggcerto.pagcertosdk.model.payments.request.Simulation;
import br.com.paggcerto.pagcertosdk.model.payments.request.SplitterRequest;
import br.com.paggcerto.pagcertosdk.model.payments.request.TransactionsToAnticipate;
import br.com.paggcerto.pagcertosdk.model.payments.response.AnticipableTransaction;
import br.com.paggcerto.pagcertosdk.model.payments.response.Anticipation;
import br.com.paggcerto.pagcertosdk.model.payments.response.AnticipationHistory;
import br.com.paggcerto.pagcertosdk.model.payments.response.BankSlipPayment;
import br.com.paggcerto.pagcertosdk.model.payments.response.BankSlipPercentages;
import br.com.paggcerto.pagcertosdk.model.payments.response.BankSlipTransactionLink;
import br.com.paggcerto.pagcertosdk.model.payments.response.BankSlipsList;
import br.com.paggcerto.pagcertosdk.model.payments.response.Bin;
import br.com.paggcerto.pagcertosdk.model.payments.response.CanAnticipate;
import br.com.paggcerto.pagcertosdk.model.payments.response.CardResponse;
import br.com.paggcerto.pagcertosdk.model.payments.response.CardResponseList;
import br.com.paggcerto.pagcertosdk.model.payments.response.CardTransactionLink;
import br.com.paggcerto.pagcertosdk.model.payments.response.CheckoutBankSlip;
import br.com.paggcerto.pagcertosdk.model.payments.response.Commission;
import br.com.paggcerto.pagcertosdk.model.payments.response.Fee;
import br.com.paggcerto.pagcertosdk.model.payments.response.FeeBase;
import br.com.paggcerto.pagcertosdk.model.payments.response.FutureTransaction;
import br.com.paggcerto.pagcertosdk.model.payments.response.Geolocation;
import br.com.paggcerto.pagcertosdk.model.payments.response.HistoryPayment;
import br.com.paggcerto.pagcertosdk.model.payments.response.MobileDevice;
import br.com.paggcerto.pagcertosdk.model.payments.response.OtherTransactionLink;
import br.com.paggcerto.pagcertosdk.model.payments.response.Payment;
import br.com.paggcerto.pagcertosdk.model.payments.response.PaymentDevice;
import br.com.paggcerto.pagcertosdk.model.payments.response.RequestedCardTransactionsList;
import br.com.paggcerto.pagcertosdk.model.payments.response.SimulationResult;
import br.com.paggcerto.pagcertosdk.model.payments.response.SplitterResponse;
import br.com.paggcerto.pagcertosdk.model.payments.response.SplitterResponseList;
import br.com.paggcerto.pagcertosdk.model.payments.response.StatisticList;
import br.com.paggcerto.pagcertosdk.model.payments.response.StatisticPayment;
import br.com.paggcerto.pagcertosdk.model.payments.response.TransactionsList;
import br.com.paggcerto.pagcertosdk.model.payments.response.TransferFee;

public class PaymentTest {
    private void test1(){

        List<String> idList = Arrays.asList("a0b0", "a1b1", "a2b2");

        BankAccountRequest bankAccount = new BankAccountRequest();
        bankAccount.setHolderName("Maria dos Santos");
        bankAccount.setTaxDocument("123.123.123-87");
        bankAccount.setBankNumber("001");
        bankAccount.setAccountNumber("123456-78");
        bankAccount.setBankBranchNumber("1234-5");
        bankAccount.setVariation(null);
        bankAccount.setType("corrente");

        AddressRequest address = new AddressRequest();
        address.setCityCode("2800308");
        address.setDistrict("Inacio Barbosa");
        address.setLine1("R Manoel De Oliveira Martins");
        address.setLine2("Ap 001, Cleveland House");
        address.setStreetNumber("229");
        address.setZipCode("49040-830");

        SplitterRequest splitterRequest = new SplitterRequest("Produtos Maria LTDA", 32, true, address, bankAccount);

        FilterSplitterTransaction filterSplitterTransaction = new FilterSplitterTransaction();

        PaymentNetwork paymentNetwork = new PaymentNetwork();
        paymentNetwork.futureTransactionSplitterDetail("date", filterSplitterTransaction, new PagcertoCallBack<TransactionsList>() {
            @Override
            public void onSuccess(TransactionsList obj) {

            }

            @Override
            public void onError(int code, @NotNull String message) {

            }
        });
    }
}
