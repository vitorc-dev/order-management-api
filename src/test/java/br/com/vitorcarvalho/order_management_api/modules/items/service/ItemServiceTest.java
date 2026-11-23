package br.com.vitorcarvalho.order_management_api.modules.items.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vitorcarvalho.order_management_api.modules.exceptions.ItemNotFoundException;
import br.com.vitorcarvalho.order_management_api.modules.items.ItemEntity;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.CreateItemRequest;
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

    //Create
    @Test
    void shouldCreateItem(){
        CreateItemRequest dto = new CreateItemRequest();
        ItemEntity item = new ItemEntity();

        when(itemMapper.toEntity(dto)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(item);

        ItemEntity result = itemService.create(dto);

        assertEquals(item, result);
        verify(itemMapper).toEntity(dto);
        verify(itemRepository).save(item);
    }

    //findByFilter
    @Test
    void shouldFilterByNameWhenNameIsProvided(){
        List<ItemEntity> items = List.of(new ItemEntity());

        when(itemRepository.findByNameContainingIgnoreCase("HOBBIT")).thenReturn(items);

        List<ItemEntity> result = itemService.findByFilter("hobbit", null);

        assertEquals(items, result);
        verify(itemRepository).findByNameContainingIgnoreCase("HOBBIT");
    }

    @Test
    void shouldFilterByCategoryWhenOnlyCategoryIsProvided(){
        List<ItemEntity> items = List.of(new ItemEntity());

        when(itemRepository.findByCategoryContainingIgnoreCase("LIVROS")).thenReturn(items);

        List<ItemEntity> result = itemService.findByFilter(null, "livros");

        assertEquals(items, result);
        verify(itemRepository).findByCategoryContainingIgnoreCase("LIVROS");
        verify(itemRepository, never()).findByNameContainingIgnoreCase(any());
    }

    @Test
    void shouldReturnAllItemsWhenNoFilterIsProvided(){
        List<ItemEntity> items = List.of(new ItemEntity());

        when(itemRepository.findAll()).thenReturn(items);

        List<ItemEntity> result = itemService.findByFilter(null, null);

        assertEquals(items, result);
        verify(itemRepository).findAll();
        verify(itemRepository, never()).findByNameContainingIgnoreCase(any());
        verify(itemRepository, never()).findByCategoryContainingIgnoreCase(any());
    }

    @Test
    void shouldIgnoreCategoryWhenNameIsProvided(){
        List<ItemEntity> items = List.of(new ItemEntity());

        when(itemRepository.findByNameContainingIgnoreCase("HOBBIT")).thenReturn(items);

        List<ItemEntity> result = itemService.findByFilter("hobbit", "livros");

        assertEquals(items, result);
        verify(itemRepository, never()).findByCategoryContainingIgnoreCase(any());
        verify(itemRepository).findByNameContainingIgnoreCase("HOBBIT");
    }
}
