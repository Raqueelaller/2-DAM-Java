package com.grupos_musicales.grupos_musicales.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.grupos_musicales.grupos_musicales.model.Grupo;

public interface GrupoRepository extends MongoRepository <Grupo,String> {

	// Busca grupos que tengan un estilo específico en su lista de estilos
	 List<Grupo> findByEstilosContaining(String estilo);
	 // Busca grupos que tengan un álbum con un nombre exacto
	 List<Grupo> findByAlbumesNombre(String nombreAlbum);
	 // Busca grupos de un país cuyos álbumes sean de un año específico
	 List<Grupo> findByAlbumesFecha(String fecha);
	
	 Grupo findByNombre(String nombre);
	 
	    @Query(value = "{ 'albumes.nombre': { $regex: ?0, $options: 'i' } }",
	            fields = "{ 'nombre': 1, 'albumes.$': 1 }")
	     List<Grupo> buscarAlbumPorSubcadena(String subcadena);
}
