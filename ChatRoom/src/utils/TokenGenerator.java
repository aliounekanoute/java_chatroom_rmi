package utils;

public class TokenGenerator {
	private final String ALPHA_NUMERIC_STRING = "azertyuiopqsdfghjklmwxcvbn-_@ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public TokenGenerator() {}
	
	
	private String randomAlphaNumeric() {

		StringBuilder builder = new StringBuilder();
		int count = (int) (Math.random()*(ALPHA_NUMERIC_STRING.length() - 10) + 1);
	
		while (count-- != 0) {
	
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	
		}
	
		return builder.toString();

	}
	
	public String getToken()
	{
		return this.randomAlphaNumeric();
	}

}
