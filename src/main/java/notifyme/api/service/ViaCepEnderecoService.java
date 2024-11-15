package notifyme.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import notifyme.api.dto.EnderecoDTO;

@Service
public class ViaCepEnderecoService {
    
    public EnderecoDTO buscarEnderecoPeloCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("https://viacep.com.br/ws/%s/json/", cep);
        ResponseEntity<EnderecoDTO> response = restTemplate.getForEntity(url, EnderecoDTO.class);

        return response.getBody();
    }
}
