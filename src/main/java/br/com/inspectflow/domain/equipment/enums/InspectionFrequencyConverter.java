package br.com.inspectflow.domain.equipment.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InspectionFrequencyConverter implements AttributeConverter<InspectionFrequency,Integer> {
    @Override
    public Integer convertToDatabaseColumn(InspectionFrequency attribute) {
        return (attribute == null) ? null : attribute.getDias();
    }

    @Override
    public InspectionFrequency convertToEntityAttribute(Integer dbData) {
        return (dbData == null) ? null : InspectionFrequency.fromDias(dbData);
    }
}
