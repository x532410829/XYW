package com.Panacea.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。
 * @author 夜未
 * @since 2020年11月27日
 */
public class StreamDemo {


public static void main(String[] args) {
	
	List<String> str = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");

		//创建流 
		str.stream();  //-为集合创建串行流。
		str.parallelStream();  //− 为集合创建并行流。
		str.forEach(System.out::println);

		//对于基本数值型，目前有三种对应的包装类型 Stream：IntStream、LongStream、DoubleStream。

		String [] strArray = new String[] {"a", "b", "c"};
		Stream<String> stream = Stream.of("a", "b", "c");
		stream = Stream.of(strArray);
		stream = Arrays.stream(strArray);
		
		stream.map(s ->s.toUpperCase()).forEach(System.out::println);
		//流结束会被关闭，需要重新获取流
		stream = Arrays.stream(strArray);
		List<String>list= stream.map(s ->s.toUpperCase()).collect(Collectors.toList());
		System.out.println(list);


	}
	
}
