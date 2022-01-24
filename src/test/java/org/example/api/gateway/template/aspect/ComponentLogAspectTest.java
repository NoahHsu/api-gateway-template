package org.example.api.gateway.template.aspect;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ComponentLogAspectTest {

    @InjectMocks
    private ComponentLogAspect componentLogAspect;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @BeforeEach
    public void setup() throws NoSuchMethodException {
        final MethodSignature mockSignature = mock(MethodSignature.class);
        when(mockSignature.getMethod()).thenReturn(String.class.getMethod("toString"));
        when(mockSignature.getDeclaringType()).thenReturn(String.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(mockSignature);
    }

    @Test
    void testAspectOk() {
        try {
            componentLogAspect.aroundComponent(proceedingJoinPoint);
            verify(proceedingJoinPoint, times(1)).proceed();
        } catch (Throwable e) {
            fail();
        }
    }

}
