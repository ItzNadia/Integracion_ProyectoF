package servicios;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("ws")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(servicios.CatalogoWS.class);
        resources.add(servicios.CategoriaWS.class);
        resources.add(servicios.HatoWS.class);
        resources.add(servicios.MovimientoWS.class);
        resources.add(servicios.RanchoWS.class);
        resources.add(servicios.SesionWS.class);
        resources.add(servicios.UsuarioWS.class);
    }
    
}
