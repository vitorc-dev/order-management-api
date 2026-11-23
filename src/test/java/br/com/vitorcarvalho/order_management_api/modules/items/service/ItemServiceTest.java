package br.com.vitorcarvalho.order_management_api.modules.items.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vitorcarvalho.order_management_api.modules.exceptions.ItemNotFoundException;
import br.com.vitorcarvalho.order_management_api.modules.items.ItemEntity;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.PatchItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.UpdateItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.mappers.ItemMapper;
import br.com.vitorcarvalho.order_management_api.modules.items.repositories.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    //Delete
    @Test
    void shouldDeleteItemWhenItemExists(){
        ItemEntity item = new ItemEntity();
        UUID id = UUID.randomUUID();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        itemService.delete(id);
        verify(itemRepository).delete(item);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingItem(){
        UUID id = UUID.randomUUID();

        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> itemService.delete(id));
        verify(itemRepository, never()).delete(any());
    }

    //Patch
    @Test
    void shouldPatchItemWhenItemExists(){
        UUID id = UUID.randomUUID();
        ItemEntity item = new ItemEntity();
        PatchItemRequest dto = new PatchItemRequest();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemRepository.save(item)).thenReturn(item);

        ItemEntity result = itemService.patch(id, dto);

        assertEquals(item, result);
        verify(itemRepository).save(result);
        verify(itemMapper).patchEntityFromDTO(dto, item);
    }

    @Test
    void shouldThrowWhenPatchingNonExistingItem(){
        UUID id = UUID.randomUUID();
        PatchItemRequest dto = new PatchItemRequest();
        
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.patch(id, dto));
        verify(itemMapper, never()).patchEntityFromDTO(any(), any());
        verify(itemRepository, never()).save(any());
    }

    //Update
    @Test
    void shouldUpdateWhenItemExists(){
        UUID id = UUID.randomUUID();
        ItemEntity item = new ItemEntity();
        UpdateItemRequest dto = new UpdateItemRequest();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemRepository.save(item)).thenReturn(item);

        ItemEntity result = itemService.update(id, dto);

        assertEquals(item, result);
        verify(itemMapper).updateEntityFromDTO(dto, item);
        verify(itemRepository).save(result);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingItem(){
        UUID id = UUID.randomUUID();
        UpdateItemRequest dto = new UpdateItemRequest();

        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        
        assertThrows(ItemNotFoundException.class, () -> itemService.update(id, dto));
        verify(itemMapper, never()).updateEntityFromDTO(any(), any());
        verify(itemRepository, never()).save(any());
    }
}
