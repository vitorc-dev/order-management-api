package br.com.vitorcarvalho.order_management_api.modules.items.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitorcarvalho.order_management_api.modules.items.ItemEntity;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.CreateItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.PatchItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.dto.UpdateItemRequest;
import br.com.vitorcarvalho.order_management_api.modules.items.service.ItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;




@RestController
@RequestMapping("/items")
@Tag(name = "Item", description = "Item's information")
public class ItemController {
    private ItemUseCase itemUseCase;

    ItemController(ItemUseCase itemUseCase){
        this.itemUseCase = itemUseCase;
    }

    @PostMapping("/")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "The item was created with success!"),
        @ApiResponse(responseCode = "400", description = "Please verify the item's attributes.")
    })
    @Operation(summary = "Registration", description = "This function is responsible for recording the items.")
    public ResponseEntity<ItemEntity> create(@Valid @RequestBody CreateItemRequest createdItem) {
        ItemEntity item = this.itemUseCase.create(createdItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("")
    @Operation(summary = "List", description = "This funcion is responsible for listing the items based on a filter.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "The list was generated with success!")
    })
    public ResponseEntity<List<ItemEntity>> list(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        List<ItemEntity> items = itemUseCase.findByFilter(name, category);
        return ResponseEntity.ok(items);
    }
    
    @PatchMapping("/{id}")
    @Operation(summary = "Patch", description = "This function is responsible for updating partially an item by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "The item was patched with success!"),
        @ApiResponse(responseCode = "404", description = "Please verify the ID provided.")
    })
    public ResponseEntity<ItemEntity> patch(@PathVariable UUID id, @Valid @RequestBody PatchItemRequest patchedItemRequest) {
        ItemEntity patchedItem = this.itemUseCase.patch(id, patchedItemRequest);
        return ResponseEntity.ok(patchedItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "This funcion is responsible for updating an item by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "The item was updated with success!"),
        @ApiResponse(responseCode = "404", description = "Please verify the ID provided.")
    })
    public ResponseEntity<ItemEntity> update(@PathVariable UUID id, @Valid @RequestBody UpdateItemRequest updatedItemRequest) {
        ItemEntity updatedItem = this.itemUseCase.update(id, updatedItemRequest);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete", description = "This function is responsible for deleting an item by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "The item was deleted with success!"),
        @ApiResponse(responseCode = "404", description = "Please verify the ID provided.")
    })
    public void delete(@PathVariable UUID id){
        itemUseCase.delete(id);
    }


}
