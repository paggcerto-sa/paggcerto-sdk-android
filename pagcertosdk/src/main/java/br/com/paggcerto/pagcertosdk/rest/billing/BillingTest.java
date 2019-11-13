package br.com.paggcerto.pagcertosdk.rest.billing;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import br.com.paggcerto.pagcertosdk.PagcertoCallBack;
import br.com.paggcerto.pagcertosdk.model.billing.request.BillingRequest;
import br.com.paggcerto.pagcertosdk.model.billing.request.FilterBilling;
import br.com.paggcerto.pagcertosdk.model.billing.response.Billing;
import br.com.paggcerto.pagcertosdk.model.billing.response.BillingDetails;
import br.com.paggcerto.pagcertosdk.model.billing.response.ListBillings;

public class BillingTest {
    private void test(){
        BillingDetails billingDetails = new BillingDetails("c3d3", "Compra de acessórios.", "Descrição", 1, 50.99);
        List<BillingDetails> listBillingDetails = Collections.singletonList(billingDetails);

        BillingRequest billingRequest = new BillingRequest();
        billingRequest.setSellingKey("Xtd89");
        billingRequest.setFullName("Valentina dos Santos");
        billingRequest.setDocument("12312312387");
        billingRequest.setPhoneNumber("79988887777");
        billingRequest.setEmail("valentina@email.com");
        billingRequest.setPayment("both");
        billingRequest.setFixedInstallments(true);
        billingRequest.setMaximumInstallments(1);
        billingRequest.setDiscountCard(5.0);
        billingRequest.setDiscountBankSlip(3.0);
        billingRequest.setDueDate("2019-03-30");
        billingRequest.setBillingDetails(listBillingDetails);

        BillingNetwork billingNetwork = new BillingNetwork();
        billingNetwork.createBilling(billingRequest, new PagcertoCallBack<Billing>() {
            @Override
            public void onSuccess(Billing obj) {

            }

            @Override
            public void onError(int code, @NotNull String message) {

            }
        });
    }

    private void test2(){
        FilterBilling filterBilling = new FilterBilling();

        BillingNetwork billingNetwork = new BillingNetwork();
        billingNetwork.listBillings(filterBilling, new PagcertoCallBack<ListBillings>() {
            @Override
            public void onSuccess(ListBillings obj) {

            }

            @Override
            public void onError(int code, @NotNull String message) {

            }
        });
    }

    private void test3(){
        BillingNetwork billingNetwork = new BillingNetwork();
        billingNetwork.getBilling("idBilling", new PagcertoCallBack<Billing>() {
            @Override
            public void onSuccess(Billing obj) {

            }

            @Override
            public void onError(int code, @NotNull String message) {

            }
        });
    }

    private void test4(){
        BillingNetwork billingNetwork = new BillingNetwork();
        billingNetwork.cancelBilling("idBilling", new PagcertoCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean obj) {

            }

            @Override
            public void onError(int code, @NotNull String message) {

            }
        });
    }
}
