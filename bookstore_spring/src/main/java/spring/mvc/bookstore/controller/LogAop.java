package spring.mvc.bookstore.controller;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogAop {

	//ReturnType�� Object�� �ƴϸ� Mapper�� ���� �۵����� ����
	public Object LoggerAop(ProceedingJoinPoint joinPoint) throws Throwable {
		String signatureStr = joinPoint.getSignature().toShortString();  //�ٽɰ����� ���� : Ŭ������.�޼����()
		System.out.println(signatureStr + " is START");

		Object obj = null;
		try {
			 obj = joinPoint.proceed();  //�ٽɰ��� ȣ��. Throwable �߻�
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(signatureStr + " is END");
		}
		
		return obj;
	}
}
