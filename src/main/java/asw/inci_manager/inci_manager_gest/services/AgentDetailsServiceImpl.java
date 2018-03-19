package asw.inci_manager.inci_manager_gest.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.repositories.AgentRepository;


@Service("userDetailsService")
public class AgentDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AgentRepository agentsRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Agent agent = agentsRepository.findByEmail(email);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		return new org.springframework.security.core.userdetails.User(agent.getEmail(), agent.getPassword(),
				grantedAuthorities);
	}
}