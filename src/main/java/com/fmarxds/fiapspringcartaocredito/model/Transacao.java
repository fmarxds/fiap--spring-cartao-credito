package com.fmarxds.fiapspringcartaocredito.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Field("estabelecimento")
    private String estabelecimento;

    @Field("valor")
    private BigDecimal valor;

    @Field("tipo")
    private TipoTransacaoEnum tipo;

    @Field("parcelas")
    private Integer parcelas;

    @Field("data_hora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

}
