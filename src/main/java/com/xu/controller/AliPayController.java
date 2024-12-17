package com.xu.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.xu.common.AliPayConfig;
import com.xu.mapper.PenaltyServiceMapper;
import com.xu.pojo.OverdueRecords;
import com.xu.pojo.Result;
import com.xu.service.PenaltyService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import javax.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("alipay")
@Transactional(rollbackFor = Exception.class)
public class AliPayController {

    @Resource
    AliPayConfig aliPayConfig;

    @Resource
    private PenaltyServiceMapper penaltyServiceMapper;

    private static final String GATEWAY_URL ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT ="JSON";
    private static final String CHARSET ="utf-8";
    private static final String SIGN_TYPE ="RSA2";
    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(Integer overdueId, HttpServletResponse httpResponse) throws Exception {
//        查询逾期记录信息
        OverdueRecords overdueRecords=penaltyServiceMapper.getRecordById(overdueId);
        if(overdueRecords==null){
            return ;
        }

        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        //创建request并设置参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
//        JSONObject bizContent=new JSONObject();
//        bizContent.put("out_trade_no",overdueRecords.getOverdueId());
//        bizContent.put("total_amount",overdueRecords.getOverdueDays());
//        bizContent.put("subject","罚金");
//        bizContent.put("timeout_express", "10m");
//        bizContent.put("product_code","FAST_INSTANT_TRADE_PAY");
//        request.setBizContent(bizContent.toString());
//        request.setReturnUrl("https://localhost:8080/still");
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = String.valueOf(overdueRecords.getOverdueId());
        //付款金额，必填
        String total_amount = String.valueOf(overdueRecords.getPenalty());
        //订单名称，必填
        String subject = "逾期罚金";
        //商品描述，可空
        String body = "下次可不要逾期了哦";
        String timeout = "10m";

        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                 System.out.println(name + " = " + request.getParameter(name));
            }
            int overdueId = Integer.parseInt(request.getParameter("out_trade_no"));
            penaltyServiceMapper.updateRecords(overdueId);

//            String tradeNo = params.get("out_trade_no");
//            String gmtPayment = params.get("gmt_payment");
//            String alipayTradeNo = params.get("trade_no");
//            // 支付宝验签

//                // 验签通过
//                System.out.println("商户订单号: " + params.get("out_trade_no"));
//                System.out.println("交易名称: " + params.get("subject"));
//                System.out.println("交易状态: " + params.get("trade_status"));
//                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
//                System.out.println("商户订单号: " + params.get("out_trade_no"));
//                System.out.println("交易金额: " + params.get("total_amount"));
//                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
//                System.out.println("买家付款时间: " + params.get("gmt_payment"));
//                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                // 更新订单未已支付
//                Orders order = new Orders();
//                order.setId(Long.valueOf(tradeNo));
//                order.setStatus(2);
////                order.setCheckoutTime(params.get("gmt_payment"));
//                shopOrderMapper.updateById(order);

        }
        return "success";
    }
}