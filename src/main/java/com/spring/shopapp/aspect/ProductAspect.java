package com.spring.shopapp.aspect;

import com.spring.shopapp.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class ProductAspect {

    @Before("(execution(* com.spring.shopapp.service.ProductService.save(..)) " +
            "|| execution(* com.spring.shopapp.service.ProductService.update(..)) " +
            "|| execution(* com.spring.shopapp.service.ProductService.delete(..))" +
            ") && args(product)")
    public void logOperations(JoinPoint joinPoint, Product product) {
        if (product.getName().length() <= 1) {
            throw new IllegalArgumentException("Invalid product name: " + product.getName() + ". Should be more than 1.");
        }
        String methodName = joinPoint.getSignature().getName();
        log.info("Preforming operation {} on product: {}", methodName, product);
    }

    @AfterReturning(pointcut = "execution(* com.spring.shopapp.service.ProductService.getById(..))", returning = "product")
    public void logGettingProduct(Product product) {
        log.info("Fetched product: {}", product);
    }

    @Around("execution(* com.spring.shopapp.service.ProductService.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.info("Execution time for method: {} : {} ms", joinPoint.getSignature().getName(), (endTime - startTime));
        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.spring.shopapp.service.ProductService.*(..))", throwing = "ex")
    public void handleExceptions(JoinPoint joinPoint, Exception ex) {
        log.info("Exception occurred in method: {}. Message: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }


    private final Map<Long, Product> productCache = new ConcurrentHashMap<>();

    @Around("execution(* com.spring.shopapp.service.ProductService.getById(..)) && args(id)")
    public Object cacheGetById(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
        if (productCache.containsKey(id)) {
            return productCache.get(id);
        }

        Object result = joinPoint.proceed();
        if (result instanceof Product) {
            productCache.put(id, (Product) result);
        }

        return result;
    }

    @After("execution(* com.spring.shopapp.service.ProductService.save(..))")
    public void sendCreateNotification(JoinPoint joinPoint) {
        log.info("New product created: {}", joinPoint.getSignature().getName());
    }

}
