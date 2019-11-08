package com.madbarsoft.JWS;

import java.util.Arrays;
import java.util.List;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;


public class JWSMainCls {

	public static void main(String[] args) throws Exception {

		  System.out.println("================== jws app start =======================");
		  
		  // #####################    SENDER'S SIDE    ############################
	       
	       JwtClaims claims = new JwtClaims();                // REGISTER CLAIMS
	       claims.setAudience("Admins");    // Who are audience this token
	       claims.setExpirationTimeMinutesInTheFuture(10);
	       claims.setGeneratedJwtId();
	       claims.setIssuer("com.madbarsoftauth.com");       // Who issue this token ?
	       claims.setNotBeforeMinutesInThePast(2);
	       claims.setSubject("com.madbarsoft.com");         // For token
	       
	       // PRIVATE CLAIMS
	       
	       claims.setClaim("email", "imranmadbar@gmail.com");   
	       claims.setClaim("role", "ADMIN");
	       List<String> subjects = Arrays.asList("JAVA", "SQL", "ANDROID");
	       claims.setClaim("subject", subjects);
	       
	       System.out.println("CLAIMS: "+claims.toJson());
	       
	       
	       // Create Signature
	       
	       RsaJsonWebKey jsonSignKey = RsaJwkGenerator.generateJwk(2048);
	       //System.out.println("RsaJsonWebKey Private Key: "+jsonSignKey);
	       
	       
	       JsonWebSignature jWebSign = new JsonWebSignature();
	       jWebSign.setKey(jsonSignKey.getPrivateKey());
	       
	       
	       
	       jWebSign.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
	       jWebSign.setPayload(claims.toJson());
	       
	       System.out.println("jWebSign: "+jWebSign);
	       
	       
	       
	       String signatureOfJwt = jWebSign.getCompactSerialization();   // Generating Signature
	       
	       System.out.println("signatureOfJwt: "+signatureOfJwt);
	       
	       
	       
	       // ###############################    RECEIVER SIDE      ##########################
	       
	       JwtConsumer consumer = new JwtConsumerBuilder()
	    		   					.setExpectedAudience("Admins")
	    		   					.setExpectedIssuer("com.madbarsoftauth.com")
	    		   					.setVerificationKey(jsonSignKey.getPublicKey())
	    		   					.setRequireSubject()
	    		   					.build();
	       
	       JwtClaims receiveClaims = consumer.processToClaims(signatureOfJwt);
	       
	       System.out.println("IS VAAIDE CLAIMS: "+receiveClaims.toJson());
	       
	       
	       
	       
	       

	}

}
