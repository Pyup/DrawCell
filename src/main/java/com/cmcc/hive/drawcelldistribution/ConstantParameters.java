package com.cmcc.hive.drawcelldistribution;

import java.math.BigDecimal;

public class ConstantParameters {
	private static final double tmp1 = 46;
	private static final double tmp2 = 60;
	private static final int decimalscale = 6;
	public static final BigDecimal num1 = new BigDecimal(Double.toString(tmp1));
	public static final BigDecimal num2 = new BigDecimal(Double.toString(tmp2));
	public static final BigDecimal tmpfloat = num1.divide(num2,decimalscale,BigDecimal.ROUND_HALF_UP);
	public static final BigDecimal tmpminlongitude = new BigDecimal(Double.toString(113));
    public static final BigDecimal minlongitude =  tmpminlongitude.add(tmpfloat);	
    public static final BigDecimal maxlongitude = new BigDecimal("114").add(new BigDecimal("37").divide(new BigDecimal("60"),decimalscale,BigDecimal.ROUND_HALF_UP)); 
	public static final BigDecimal minlatitude = new BigDecimal("22").add(new BigDecimal("27").divide(new BigDecimal("60"),decimalscale,BigDecimal.ROUND_HALF_UP));
	public static final BigDecimal maxlatitude = new BigDecimal("22").add(new BigDecimal("52").divide(new BigDecimal("60"),decimalscale,BigDecimal.ROUND_HALF_UP));
	
	public static final BigDecimal steps = new BigDecimal("100");
	public static final BigDecimal longitudestep = maxlongitude.subtract(minlongitude).divide(steps,decimalscale,BigDecimal.ROUND_HALF_UP);
	public static final BigDecimal latitudestep = maxlatitude.subtract(minlatitude).divide(steps,decimalscale,BigDecimal.ROUND_HALF_UP);
	
}
