package cod.restaurantapi.common.config;

import cod.restaurantapi.common.parameter.repository.RMAParameterRepository;
import cod.restaurantapi.common.parameter.repository.entity.RMAParameterEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class RMACurrencyConfiguration {

    @Bean
    @Qualifier("currency")
    String currency(RMAParameterRepository rmaParameterRepository) {
        Optional<RMAParameterEntity> parameterEntity = rmaParameterRepository.findByName("Currency");
        return parameterEntity.orElseThrow().getDefinition();
    }

}
