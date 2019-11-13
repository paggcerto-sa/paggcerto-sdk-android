package br.com.paggcerto.pagcertosdk.rest.account;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import br.com.paggcerto.pagcertosdk.PagcertoCallBack;
import br.com.paggcerto.pagcertosdk.model.account.request.Address;
import br.com.paggcerto.pagcertosdk.model.account.request.BankAccount;
import br.com.paggcerto.pagcertosdk.model.account.request.Company;
import br.com.paggcerto.pagcertosdk.model.account.request.CreateAccountRequest;
import br.com.paggcerto.pagcertosdk.model.account.request.FilterProfile;
import br.com.paggcerto.pagcertosdk.model.account.request.FilterUser;
import br.com.paggcerto.pagcertosdk.model.account.request.Holder;
import br.com.paggcerto.pagcertosdk.model.account.request.PartnerClientRequest;
import br.com.paggcerto.pagcertosdk.model.account.request.PresetsRequest;
import br.com.paggcerto.pagcertosdk.model.account.request.ProfileRequest;
import br.com.paggcerto.pagcertosdk.model.account.request.ScopesList;
import br.com.paggcerto.pagcertosdk.model.account.request.TransferPlan;
import br.com.paggcerto.pagcertosdk.model.account.request.UserLogin;
import br.com.paggcerto.pagcertosdk.model.account.request.UserRequest;
import br.com.paggcerto.pagcertosdk.model.account.response.SellersList;
import br.com.paggcerto.pagcertosdk.model.account.response.UserResponse;
import br.com.paggcerto.pagcertosdk.model.account.response.UserResponseList;

public class AccountTest {
    public void test(){
        Company company = new Company(
                "Esportes ME",
                "Mariana e Emanuelly Esportes ME",
                "94.467.995/0001-49",
                "vL"
        );

        Holder holder = new Holder(
                "fullName",
                "birthDate",
                'M',
                "927.228.895-95",
                "(79) 2946-7954",
                "(79) 98827-7241",
                company
        );

        Address address = new Address(
                "2800308",
                "Inacio Barbosa",
                "R Manoel De Oliveira Martins",
                "Ap 001, Cleveland House",
                "229",
                "49040-830"
        );

        BankAccount bankAccount = new BankAccount(
                false,
                "001",
                "123456-78",
                "1234-5",
                null,
                "corrente"
        );

        UserLogin userLogin = new UserLogin(
                "valentina@email.com",
                "95625845"
        );

        TransferPlan transferPlan = new TransferPlan(32, 2, 32, true, false);

        PresetsRequest presetsRequest = new PresetsRequest(
                "95238473",
                "(79) 2946-7954",
                "(79) 98827-7241",
                "Esporte e CIA",
                "Esportes ME",
                transferPlan,
                bankAccount,
                address
        );

        ProfileRequest profileRequest = new ProfileRequest(
                "name",
                false
        );

        List<String> scopes = Arrays.asList("account.users.edit", "account.users.readonly");

        UserRequest userRequest = new UserRequest();
        FilterUser filterUser = new FilterUser();

        List<String> sellers = Arrays.asList("a0b0", "a1b1", "a2b2");

        PartnerClientRequest partnerClientRequest = new PartnerClientRequest(sellers, transferPlan);

        FilterProfile filterProfile = new FilterProfile();

        AccountNetwork accountNetwork = new AccountNetwork();
        accountNetwork.getPartnerClients(filterProfile, new PagcertoCallBack<SellersList>() {
            @Override
            public void onSuccess(SellersList obj) {

            }

            @Override
            public void onError(int code, @NotNull String message) {

            }
        });
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(
                holder,
                address,
                bankAccount,
                userLogin,
                "MA",
                "k5",
                transferPlan,
                "Esporte e CIA",
                "http://meuaplicativo.com.br/"
        );
    }
}
