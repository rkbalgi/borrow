package com.github.rkbalgi.apps.borrow

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@ResponseBody
class WebResources {


    @GetMapping("/ok")
    fun ok(): String {
        return "200 OK";
    }
}