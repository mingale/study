package spring.mvc.bookstore.controller;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogAop {

	//ReturnType이 Object가 아니면 Mapper가 정상 작동하지 않음
	public Object LoggerAop(ProceedingJoinPoint joinPoint) throws Throwable {
		String signatureStr = joinPoint.getSignature().toShortString();  //핵심관심의 정보 : 클래스명.메서드명()
		System.out.println(signatureStr + " is START");

		Object obj = null;
		try {
			 obj = joinPoint.proceed();  //핵심관심 호출. Throwable 발생
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(signatureStr + " is END");
		}
		
		return obj;
	}
}
