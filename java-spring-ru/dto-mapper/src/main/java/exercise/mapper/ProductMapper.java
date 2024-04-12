package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

// BEGIN
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public abstract class ProductMapper {

    @Mapping(target = "barcode", source = "vendorCode")
    @Mapping(target = "cost", source = "price")
    @Mapping(target = "name", source = "title")
    public abstract Product map(ProductCreateDTO productCreateDTO);

    @Mapping(target = "cost", source = "price")
    public abstract Product map(@MappingTarget Product product, ProductUpdateDTO productUpdateDTO);

    @Mapping(target = "price", source = "cost")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "vendorCode", source = "barcode")
    public abstract ProductDTO map(Product product);
}
// END
