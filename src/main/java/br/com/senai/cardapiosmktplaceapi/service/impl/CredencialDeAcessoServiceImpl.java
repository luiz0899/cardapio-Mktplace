package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Usuario;
import br.com.senai.cardapiosmktplaceapi.repository.UsuariosRepository;
import br.com.senai.cardapiosmktplaceapi.security.CredencialDeAcesso;

@Service
public class CredencialDeAcessoServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		Usuario usuario = usuariosRepository.buscarPorLogin(login);
		Preconditions.checkNotNull(usuario,"O usuario não existe");
		return new CredencialDeAcesso(usuario);
		
	}

}
