package com.tea.demo.product;

public class TeaMapper {
    public static TeaResponseDto toDto(Tea tea){
        return new TeaResponseDto(
                tea.name(),
                tea.type(),
                tea.description(),
                tea.sellPrice(),
                tea.origin(),
                tea.produceAt()
        );
    }

}
