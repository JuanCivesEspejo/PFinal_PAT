package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * TODO#4
 * Crea el repositorio para la entidad AppUser de modo que,
 * además de las operaciones CRUD, se pueda consultar el AppUser asociado
 * a un email dado
 */

public interface AppUserRepository extends CrudRepository<AppUser, Long>
{
    AppUser findByEmail(String email);



    /** @Query(value = "SELECT * FROM APP_USER WHERE EMAIL = :email", nativeQuery = true)
     * AppUser encuentraPorEmial(String email)
     */

    /** Para realizar queries más complejas se usa esta sintaxis:
     *
     * Esta query busca usuarios que han utilizado el contador clicks
     *
     *  @Query(value = "SELECT DISTINCT U.*
     *  FROM USUARIO U, OPERACION O,CONTADOR C
     *  WHERE C.NOMBRE = :nombreContador
     *  AND C.ID = O.CONTADOR_ID
     *  AND U.ID = O.USUARIO_ID;" , nativeQuery = true)
     *  List<AppUser> usuariosContador(String nombreContador);
     *
     *  Otro ejemplo para un delete, sería este:
     *
     * @Query(value = "DELETE FROM CONTADOR WHERE NOMBRE = :nombre", nativeQuery = true)
     * @Transactional
     * @Modifying
     * int borraPorNombre(String nombre);
     */

}