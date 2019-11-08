package com.madbarsoft;

import org.jose4j.jwt.JwtClaims;

public class TestCls {

	public static void main(String[] args) {

       System.out.println("================== test app =======================");
       
       JwtClaims claims = new JwtClaims();
       
       System.out.println(claims.toString());

	}

}
