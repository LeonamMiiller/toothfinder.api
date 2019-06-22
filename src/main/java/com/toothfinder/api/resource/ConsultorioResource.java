package com.toothfinder.api.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.toothfinder.api.model.consultorio;
import com.toothfinder.api.repository.ConsultorioRepository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/consultorios")
@CrossOrigin
public class ConsultorioResource {
	
	@Autowired
	private ConsultorioRepository consultorioRepository;
	
	@GetMapping
	public List<consultorio> listar () {
		return consultorioRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void inserirConsultorio(@Valid @RequestBody consultorio consultorioInserido, HttpServletResponse response){
		consultorio consultCurrentID = consultorioRepository.save(consultorioInserido);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(consultCurrentID.getId()).toUri();
		response.setHeader("Location", ((URI) uri).toASCIIString());
	}

    @GetMapping("/{codigo}")
    public consultorio buscarConsultorioPorID(@PathVariable Long codigo){
	    return consultorioRepository.findOne(codigo);
    }

}

