package br.com.feliciano.mvc.entities;

import br.com.feliciano.mvc.services.AddressService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    private Long id;
    private String cep;
    private String number;
    private String place;
    private String local;
    private Integer userId;

    public Address(String cep, String number, Integer userId) {
        this.cep = cep;
        this.number = number;
        this.userId = userId;
        this.getAddress();
    }

    private void getAddress() {
        try {
            Address address = AddressService.getAddressFromCEP(cep);
            this.setPlace(address.getPlace());
            this.setLocal(address.getLocal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Address {")
                .append("cep= '")
                .append(cep)
                .append("', number= '")
                .append(number)
                .append("', place= '")
                .append(place)
                .append("', state= '")
                .append(local)
                .append("' }")
                .toString();
    }

}
