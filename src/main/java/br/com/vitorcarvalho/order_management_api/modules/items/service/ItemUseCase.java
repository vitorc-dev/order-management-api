package br.com.vitorcarvalho.order_management_api.modules.items.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.order_management_api.modules.exceptions.ItemNotFoundException;
import br.com.vitorcarvalho.order_management_api.modules.items.ItemEntity;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.CreateItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.PatchItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.UpdateItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.mappers.ItemMapper;
import br.com.vitorcarvalho.order_management_api.modules.items.repositories.ItemRepository;

@Service
public class ItemUseCase {
    private ItemRepository itemRepository;
    private ItemMapper itemMapper;

    ItemUseCase(ItemRepository itemRepository, ItemMapper itemMapper){
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public List<ItemEntity> findAll(){
        return this.itemRepository.findAll();
    }

    public List<ItemEntity> findByFilter(String name, String category){
        if(name != null){
            return this.itemRepository.findByNameContainingIgnoreCase(name.toUpperCase());
        }
        if(category != null){
            return this.itemRepository.findByCategoryContainingIgnoreCase(category.toUpperCase());
        }
        return this.itemRepository.findAll();
    }
    public ItemEntity create(CreateItemRequest createdItem){
        ItemEntity item = this.itemMapper.toEntity(createdItem);
        return this.itemRepository.save(item);
    }

    public ItemEntity patch(UUID id, PatchItemRequest patchedItem){
        ItemEntity item = itemRepository.findById(id).orElseThrow(
            () -> new ItemNotFoundException()
        );

        itemMapper.patchEntityFromDTO(patchedItem, item);
        return itemRepository.save(item);
    }

    public ItemEntity update(UUID id, UpdateItemRequest updatedItem){
        ItemEntity item = itemRepository.findById(id).orElseThrow(
            () -> new ItemNotFoundException()
        );

        itemMapper.updateEntityFromDTO(updatedItem, item);
        return itemRepository.save(item);
    }

    public void delete(UUID id){
        ItemEntity item = itemRepository.findById(id).orElseThrow(
            () -> new ItemNotFoundException()
        );

        itemRepository.delete(item);
    }
}
