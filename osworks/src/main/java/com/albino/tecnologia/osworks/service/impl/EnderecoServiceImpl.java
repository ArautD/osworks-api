package com.albino.tecnologia.osworks.service.impl;


import com.albino.tecnologia.osworks.controller.dto.EnderecoDTO;
import com.albino.tecnologia.osworks.model.Endereco;
import com.albino.tecnologia.osworks.repository.EnderecoRepository;
import com.albino.tecnologia.osworks.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements  EnderecoService {
    private final EnderecoRepository enderecoRepository;

    @Override
    public Endereco encontrarPeloEndereco(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id Não Encontrado"));
    }

    @Override
    public List<Endereco> listarTodosEnderecos() {
        return enderecoRepository.findAll();
    }

    @Override
    public Endereco criarEndereco(EnderecoDTO enderecoDTO) {

        Endereco novoEndereco = Endereco.builder()
                .CEP(enderecoDTO.getCEP())
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .UF(enderecoDTO.getUF())
                .build();

        return enderecoRepository.save(novoEndereco);
    }

    @Override
    public Endereco atualizarEndereco(Long id,EnderecoDTO enderecoDTO) {
        Endereco enderecoAtualizado = encontrarPeloEndereco(id);

        enderecoAtualizado.setCEP(enderecoDTO.getCEP());
        enderecoAtualizado.setLogradouro(enderecoDTO.getLogradouro());
        enderecoAtualizado.setNumero(enderecoDTO.getNumero());
        enderecoAtualizado.setComplemento(enderecoDTO.getComplemento());
        enderecoAtualizado.setBairro(enderecoDTO.getBairro());
        enderecoAtualizado.setCidade(enderecoDTO.getCidade());
        enderecoAtualizado.setUF(enderecoDTO.getUF());

        return enderecoRepository.save(enderecoAtualizado);
    }

    @Override
    public void deletarEndereco(Long id) {
        Endereco enderecoDeletado = encontrarPeloEndereco(id);

        enderecoRepository.delete(enderecoDeletado);
    }
}
