package br.com.vitorcarvalho.order_management_api;

import org.junit.jupiter.api.Test;

class DebugTest {
    @Test
    void checkClass() throws Exception {
        Class<?> clazz = Class.forName("br.com.vitorcarvalho.order_management_api.modules.items.mappers.ItemMapperImpl");
        System.out.println("Classe carregada: " + clazz);
        System.out.println("Anotacoes: " + java.util.Arrays.toString(clazz.getAnnotations()));
        System.out.println("Interfaces: " + java.util.Arrays.toString(clazz.getInterfaces()));
    }
}
