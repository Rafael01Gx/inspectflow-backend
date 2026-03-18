package br.com.inspectflow.application.equipment.mappers;

import br.com.inspectflow.application.equipment.dto.CreateEquipmentRequest;
import br.com.inspectflow.domain.Inspection_item.models.InspectionItem;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;

public class EquipmentMapper {
   public static Equipment fromCreateDto(CreateEquipmentRequest dto){
       Equipment equipment = Equipment.builder()
               .name(dto.name())
               .code(dto.code().toUpperCase())
               .status(dto.status())
               .type(dto.type())
               .location(dto.location())
               .build();

       if (dto.components() != null) {
           dto.components().forEach(compDto -> {
               EquipmentComponent component = EquipmentComponent.builder()
                       .name(compDto.name())
                       .category(compDto.category())
                       .build();

               equipment.addComponent(component);

               if (compDto.items() != null) {
                   compDto.items().forEach(itemDto -> {
                       InspectionItem item = InspectionItem.builder()
                               .title(itemDto.title())
                               .description(itemDto.description())
                               .category(itemDto.category())
                               .impedimentItem(itemDto.impedimentItem())
                               .build();
                       component.addInspectionItem(item);
                   });
               }
           });
       }
       return equipment;
   }

}
