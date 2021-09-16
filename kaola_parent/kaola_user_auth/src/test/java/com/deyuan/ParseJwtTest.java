package com.deyuan;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;


public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJkZXl1YW4iLCJpZCI6IjEifQ.IHw7gQwuSSqWrsn7ZchLsXSHuBdYfukqNB4L59N8lNvSKXCPDg29NZ5Ody7eCJd5-gSvvhwERa1FFLTjIaO0eLZzRqwWYR1PzOiC3KHzmfbD9wHhskoHFkFqdDT9lUZOpbwRxroyX-C9C3ZW1vsd1B2EzSWIcsjy7KM8gRXsl4Tpvfw8i1qS6jkg49OlFDZyBtmVE8oWeNZ7ZzoP0OVcVzk7hyzv0EI1QBF0qQY2F7qGNmYprEpvGZ0BsX9Ed_HkjHUVPudsPDJaArfRK__KpFST7kbvtixX_J5sDHaKkJjRoQHoc87i86HkqAkPkaIaDOUzFvzytihzCh6g-QcCJQ";

        //公钥
         //String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqx0PtwXfWSiP94EHXhrPeDN+QxL7XXY7rrdRtFNryL8gU2dt60fhHVqbzle/s0xX+1DQ9BllQFYRlN8O6e2ahc03rX3RmP8nlc6hBuqcOrcXU23FFVBeKPayZ7+CGIUA1VayPFlBiCbhGmZ7nLAG+HNmRbomR6vZ+s7ik4txM9X5u3d0apBW37nnEF5mJRpjaCB6vfgf6mgZdc7db+vYTf0SLudj9jpLKIthkixDlBfLgZ3iGFP7NbERvzm9BbjWRfpCTLyKmfv35lqCPtldXpO4ZPd/5ytFRgANF05UiYMLNtgnFJH0IDf32TIZIpZnE7c46mglNIKkFgs4bYrOvQIDAQAB-----END PUBLIC KEY-----";
        //String publickey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqx0PtwXfWSiP94EHXhrPeDN+QxL7XXY7rrdRtFNryL8gU2dt60fhHVqbzle/s0xX+1DQ9BllQFYRlN8O6e2ahc03rX3RmP8nlc6hBuqcOrcXU23FFVBeKPayZ7+CGIUA1VayPFlBiCbhGmZ7nLAG+HNmRbomR6vZ+s7ik4txM9X5u3d0apBW37nnEF5mJRpjaCB6vfgf6mgZdc7db+vYTf0SLudj9jpLKIthkixDlBfLgZ3iGFP7NbERvzm9BbjWRfpCTLyKmfv35lqCPtldXpO4ZPd/5ytFRgANF05UiYMLNtgnFJH0IDf32TIZIpZnE7c46mglNIKkFgs4bYrOvQIDAQAB-----END PUBLIC KEY-----";
        String publickey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlQpUi36mCuDDc1IQD94ntMmUStj0J4PdwyWVSlV48gPp2DAqBK5oCaiuSsWS6lyrSFZFq/lAmKDfCLMnF6Dq8kciJQrjuOgAJ6j8YRHQyqhG75KRernWv56RwzI8/AykmSVremQKBInuC7+JpcT4MU/9xYV0kr+f2TeAVAS/J24kg3iG0Cuq2gFzdpmkf8uXMiYdCrJq9FraDGpRjMCOjGygf8XSaiwSpi3Rp+G/N1O3tmR9a741HUcgfSYeT9hA4EWMtN3WOGUrRQIEqvsjcznCv5EGKhrACTrRqHT8JNp5DgRTCdX10404tfO9WGiEXNVXIOciu4SkUbLdCF5WZwIDAQAB-----END PUBLIC KEY-----";
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);

        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
