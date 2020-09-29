package com.smartfit.smartfitapi.controller;

import com.smartfit.smartfitapi.service.PaymentService;
import com.smartfit.smartfitapi.service.shared.AccessAuthService;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/payment")
public class PaymentController {
    @Autowired
    private AccessAuthService accessAuthService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping(path = "/checkout")
    public String checkout(@RequestParam String accessToken, Model model) {
        if (!accessAuthService.isUserAccessTokenValid(accessToken)) {
            return "checkout-fail";
        }
        ServiceResult<String> result = paymentService.findUserName(accessToken);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            model.addAttribute("accessToken", accessToken);
            model.addAttribute("ownerName", result.getData());
            return "checkout";
        }
        return "checkout-fail";
    }

    @GetMapping(path = "/confirm")
    public String confirm(
            @RequestParam String accessToken,
            @RequestParam Integer monthCount
    ) {
        ServiceResult<String> result = paymentService.createOrder(accessToken, monthCount);
        if (result.getCode() == ServiceResult.ResultCode.SUCCESS) {
            return result.getData();
        }
        return "checkout-fail";
    }

    @GetMapping(path = "/checkout-success")
    public String checkoutSuccess() {
        return "checkout-success";
    }

    @GetMapping(path = "/checkout-fail")
    public String checkoutFail() {
        return "checkout-fail";
    }
}
