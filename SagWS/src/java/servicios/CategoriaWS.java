package servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.Categoria;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;

@Path("categoria")
public class CategoriaWS {

    @Context
    private UriInfo context;

    public CategoriaWS() {
    }

    @GET
    @Path("getAllCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> getAllCategorias(){
    List<Categoria> list = new ArrayList<Categoria>();
    SqlSession conn=null;
    try{
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Categoria.getAllCategorias");
    }catch(Exception ex){
        ex.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
        }
    }
        return list;
    }

    @POST
    @Path("getCategoriaById")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta getCategoriaById(
            @FormParam("idCategoria") Integer idCategoria){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            Categoria c = conn.selectOne("Categoria.getCategoriaById", idCategoria);
            
            res.setRespuesta(new JSONObject(c));
            res.setError(false);
            res.setMensaje("¡Categoría encontrada!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        }finally{
            conn.close();
        }
        return res;
    }

    @POST
    @Path("buscarCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> buscarCategorias(
            @FormParam("busqueda") String busqueda){
        List<Categoria> list = new ArrayList<Categoria>();
        SqlSession conn = null;
        try{
            conn = MyBatisUtil.getSession();
            list = conn.selectList("Categoria.buscarCategorias", busqueda);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return list;
    }

    @POST
    @Path("registrarCategoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarCategoria(
            @FormParam("idCategoria") Integer idCategoria,
            @FormParam("nombre") String nombre,
            @FormParam("activo") String activo){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idCategoria", idCategoria);
            param.put("nombre", nombre);
            param.put("activo", activo);
            
            conn.insert("Categoria.registrarCategoria", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Categoria registrada correctamente!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        }finally{
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarCategoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarCategoria(
            @FormParam("idCategoria") Integer idCategoria,
            @FormParam("nombre") String nombre,
            @FormParam("activo") String activo){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idCategoria", idCategoria);
            param.put("nombre", nombre);
            param.put("activo", activo);
            
            conn.update("Categoria.editarCategoria", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Categoria editada correctamente!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        }finally{
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarEstatusCategoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarEstatusCategoria(
            @FormParam("idCategoria") Integer idCategoria,
            @FormParam("activo") String activo){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idCategoria", idCategoria);
            param.put("activo", activo);
            
            conn.update("Categoria.editarEstatusCategoria", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("Estatus de categoria editado correctamente");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        }finally{
            conn.close();
        }
        return res;
    }
}