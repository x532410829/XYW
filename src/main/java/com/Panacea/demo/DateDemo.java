package com.Panacea.demo;

import java.text.DateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

/**
 * java 8 日期处理
 * @author 夜未
 * @since 2021年1月8日
 */
public class DateDemo {
	
	

	 public static void main(String[] args) {
//		 LocalDateTest();
//		 clockTest();
//		 equalsDate();
//		 ZoneIdTest();
//		 YearMonthTest();
//		 PeriodTest();
		 DateFormatTest();
		 
		 
	 } 
		 
		 
		/**
		 * LocalDate 年月日的操作
		 */
		public static void LocalDateTest() { 
	        LocalDate today = LocalDate.now();
	        System.out.println("今天的日期:"+today);
	        
	        int year = today.getYear();
	        int month = today.getMonthValue();
	        int day = today.getDayOfMonth();
	        System.out.println("年："+year);
	        System.out.println("月:"+month);
	        System.out.println("日:"+day);
	        
	        LocalDate date1 = LocalDate.of(2021,1,8);
	        System.out.println("自定义日期:"+date1);
	        
	        LocalDate date2 = LocalDate.of(2021,1,9);

	        if(date1.equals(date2)){
	            System.out.println("时间相等");
	        }else{
	            System.out.println("时间不等");
	        }
	        
	        MonthDay birthday = MonthDay.of(date2.getMonth(),date2.getDayOfMonth());
	        MonthDay currentMonthDay = MonthDay.from(today);

	        if(currentMonthDay.equals(birthday)){
	            System.out.println("是你的生日");
	        }else{
	            System.out.println("你的生日还没有到");
	        }
	        
	        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
	        System.out.println("一周后的日期为:"+nextWeek);
	        
	        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
	        System.out.println("一年前的日期 : " + previousYear);
	        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
	        System.out.println("一年后的日期:"+nextYear);
	        
	        if(today.isLeapYear()){
	            System.out.println("今年是闰年");
	        }else {
	            System.out.println("今年不是闰年");
	        }
	    }
	 
		/**
		 * LocalTime 时间的操作，不包含日期
		 */
		public static void LocalTimeTest() { 
			  LocalTime time = LocalTime.now();
		      System.out.println("获取当前的时间,不含有日期:"+time);
		      
		      LocalTime newTime = time.plusHours(3);
		      System.out.println("三个小时后的时间为:"+newTime);
			
		        
		        
		}
	 
		/**
		 * 系统时钟工具，获取时间戳
		 */
		public static void clockTest() {
			Instant startTime =Instant.now();
			System.out.println("Clock 时间戳: " +startTime.toEpochMilli());
			Clock clock = Clock.systemUTC();
	        System.out.println("Clock 时间戳: " + clock.millis());

	        Clock defaultClock = Clock.systemDefaultZone();
	        System.out.println("Clock 时间戳: " + defaultClock.millis());
		}
		
		/**
		 * 日期的比较
		 */
		public static void equalsDate() {
			LocalDate today = LocalDate.now();

	        LocalDate tomorrow = LocalDate.of(2022,2,6);
	        if(tomorrow.isAfter(today)){
	            System.out.println("之后的日期:"+tomorrow);
	        }

	        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
	        if(yesterday.isBefore(today)){
	            System.out.println("之前的日期:"+yesterday);
	        }
		}
		
		/**
		 * 时区转换
		 */
		public static void ZoneIdTest() {
			 ZoneId america = ZoneId.of("America/New_York");
		        LocalDateTime localtDateAndTime = LocalDateTime.now();
		        System.out.println("当前时区时间："+localtDateAndTime);
		        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localtDateAndTime, america );
		        System.out.println("转换时区: " + dateAndTimeInNewYork);
		}
	
		/**
		 * 年月的操作
		 */
		public static void YearMonthTest() {
			YearMonth currentYearMonth = YearMonth.now();
	        System.out.printf("当前年月和天数 %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
	        //过期时间，设置2020年2月
	        YearMonth creditCardExpiry = YearMonth.of(2022, Month.FEBRUARY);
	        System.out.printf("过期时间为： %s %n", creditCardExpiry);
		}
		
		
		/**
		 * 计算了当天和将来（某段时间）某一天之间相差的年、月、日、周数
		 */
		public static void  PeriodTest(){
			Instant startTime =Instant.now();//获取当前时间戳
	        LocalDate today = LocalDate.now();
	        LocalDate java8Release = LocalDate.of(2022, 1, 14);
	        Period periodToNextJavaRelease = Period.between(today, java8Release);
	        System.out.println("相差月数 : "+ periodToNextJavaRelease.toTotalMonths() );
	        
	        System.out.println("相隔年数："+ChronoUnit.YEARS.between(today, java8Release)); 
	        System.out.println("相隔月数："+ChronoUnit.MONTHS.between(today, java8Release)); 
	        System.out.println("相隔天数："+ChronoUnit.DAYS.between(today, java8Release)); 
	        System.out.println("相隔周数："+ChronoUnit.WEEKS.between(today, java8Release)); 
	        Instant endTime =Instant.now();    
	        System.out.println("相隔小时数："+ChronoUnit.HOURS.between(startTime, endTime)); 
	        System.out.println("相隔分钟数："+ChronoUnit.MINUTES.between(startTime, endTime)); 
	        System.out.println("相隔秒数："+ChronoUnit.SECONDS.between(startTime, endTime)); 
	        System.out.println("相隔毫秒数："+ChronoUnit.MILLIS.between(startTime, endTime)); 
		        
		}
		
		
		/**
		 * 日期格式解析(使用预置的日期模板去格式化)
		 */
		 public static void DateFormatTest() {
			 
		    String dayAfterTommorrow = "20210205";
		    LocalDate formatted = LocalDate.parse(dayAfterTommorrow,
		            DateTimeFormatter.BASIC_ISO_DATE);
		    System.out.println(dayAfterTommorrow+" 格式化后的日期为: "+formatted);
		    
		    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		    
		    String date="2022-02-22 12:24";
		    LocalDateTime localDate = LocalDateTime.parse(date, formatter);
			System.out.println("自定义格式化日期："+localDate);
			
			String str = localDate.format(formatter);
			System.out.println("日期转字符串："+str);
		 }
		
		
		
		

}
