package com.smartfit.smartfitapi.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.smartfit.smartfitapi.entity.UserAccess;
import com.smartfit.smartfitapi.entity.UserOrder;
import com.smartfit.smartfitapi.entity.UserPayment;
import com.smartfit.smartfitapi.model.base.AppEnumBase;
import com.smartfit.smartfitapi.repository.UserAccessRepository;
import com.smartfit.smartfitapi.repository.UserOrderRepository;
import com.smartfit.smartfitapi.repository.UserPaymentRepository;
import com.smartfit.smartfitapi.utils.ServiceResult;
import com.smartfit.smartfitapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private UserOrderRepository userOrderRepository;
    @Autowired
    private UserPaymentRepository userPaymentRepository;

    private final String clientId = "AUbNFYaqf4ERlWYNokLyzsqGTfevRfTWwoFO42rIc3aEw0qOJgPQqCkN5OKpqm484socRj2cQpHOwQ-D";
    private final String clientSecret = "EPGXeA6GL9IN2p1Y-vWlKDQH7SnAN-re5dd2z3ahw4ShyVjV05Pii1WbUqCFNnNKAzQYroGrTk31uqc0";
    private final String successUrl = "https://smartfitapi2.herokuapp.com/payment/checkout-success";
    private final String cancelUrl = "https://smartfitapi2.herokuapp.com/payment/checkout-fail";

    public ServiceResult<String> findUserName(String accessToken) {
        ServiceResult<String> result = new ServiceResult<>();
        try {
            UserAccess userAccess = userAccessRepository.findByAccessToken(accessToken);

            result.setData(userAccess.getUserProfile().getName());
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.UNKNOWN);
        }
        return result;
    }

    public ServiceResult<String> createOrder(String accessToken, Integer monthCount) {
        ServiceResult<String> result = new ServiceResult<>();
        try {
            Payment payment = createPayment(monthCount);
            String mode = "sandbox";
            APIContext apiContext = new APIContext(clientId, clientSecret, mode);
            Payment createdPayment = payment.create(apiContext);

            for (Links links : createdPayment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    result.setData("redirect:" + links.getHref());
                    result.setCode(ServiceResult.ResultCode.SUCCESS);
                }
            }

            UserOrder userOrder = userAccessRepository
                    .findByAccessToken(accessToken)
                    .getUserProfile()
                    .getUserOrder();
            userOrder.setType(AppEnumBase.OrderType.MONTHLY.getString());
            userOrder.setExpiryTime(Utils.plusDayToCurrentDate(30 * monthCount));
            userOrder = userOrderRepository.save(userOrder);
            UserPayment userPayment = UserPayment.builder()
                    .userOrder(userOrder)
                    .type(AppEnumBase.PaymentType.PAYPAL.getString())
                    .status(AppEnumBase.ActivityStatus.DONE.getString())
                    .description("Purchased " + monthCount + " months subscription.")
                    .totalPrice(monthCount)
                    .build();
            userPaymentRepository.save(userPayment);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.FAIL);
        }
        return result;
    }

    public Payment createPayment(Integer monthCount) {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(monthCount.toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(successUrl);
        redirectUrls.setCancelUrl(cancelUrl);
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }
}
