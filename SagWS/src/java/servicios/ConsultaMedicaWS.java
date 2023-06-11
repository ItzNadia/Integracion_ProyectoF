package servicios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.ConsultaMedica;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;

@Path("consultaMedica")
public class ConsultaMedicaWS {

    @Context
    private UriInfo context;

    public ConsultaMedicaWS() {
    }

    @GET
    @Path("getConsultasMedicasByIdHato/{idHato}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsultaMedica> getConsultaMedicasByIdHato(@PathParam("idHato") Integer idHato) {
        List<ConsultaMedica> list = new LinkedList<>();
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            list = conn.selectList("ConsultaMedica.getConsultasMedicasByIdHato", idHato);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    @GET
    @Path("getConsultasMedicasByIdRancho/{idRancho}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsultaMedica> getConsultaMedicasByIdRancho(@PathParam("idRancho") Integer idRancho) {
        List<ConsultaMedica> list = new LinkedList<>();
        SqlSession conn = null;
        try {
            conn = MyBatisUtil.getSession();
            list = conn.selectList("ConsultaMedica.getConsultasMedicasByIdRancho", idRancho);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    @POST
    @Path("buscarConsultasMedicas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsultaMedica> buscarConsultasMedicas(
            @FormParam("idRancho") Integer idRancho,
            @FormParam("busqueda") String busqueda) {
        List<ConsultaMedica> list = new LinkedList<>();
        SqlSession conn = null;
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idRancho", idRancho);
            param.put("busqueda", busqueda);

            conn = MyBatisUtil.getSession();
            list = conn.selectList("ConsultaMedica.buscarConsultasMedicas", param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return list;
    }

    @POST
    @Path("registrarConsultaMedica")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarConsultaMedica(
            @FormParam("idHato") Integer idHato,
            @FormParam("nombreVeterinario") String nombreVeterinario,
            @FormParam("fechaAtencion") String fechaAtencion,
            @FormParam("observaciones") String observaciones,
            @FormParam("idMotivoAtencion") Integer idMotivoAtencion,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioAlta") Integer idUsuarioAlta) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idHato", idHato);
            param.put("nombreVeterinario", nombreVeterinario);
            param.put("fechaAtencion", fechaAtencion);
            param.put("observaciones", observaciones);
            param.put("idMotivoAtencion", idMotivoAtencion);
            param.put("idRancho", idRancho);
            param.put("idUsuarioAlta", idUsuarioAlta);

            conn.insert("ConsultaMedica.registrarConsultaMedica", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Consulta médica registrada correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        } finally {
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarConsultaMedica")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarConsultaMedica(
            @FormParam("idConsultaMedica") Integer idConsultaMedica,
            @FormParam("idHato") Integer idHato,
            @FormParam("nombreVeterinario") String nombreVeterinario,
            @FormParam("fechaAtencion") String fechaAtencion,
            @FormParam("observaciones") String observaciones,
            @FormParam("idMotivoAtencion") Integer idMotivoAtencion,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idConsultaMedica", idConsultaMedica);
            param.put("idHato", idHato);
            param.put("nombreVeterinario", nombreVeterinario);
            param.put("fechaAtencion", fechaAtencion);
            param.put("observaciones", observaciones);
            param.put("idMotivoAtencion", idMotivoAtencion);
            param.put("idRancho", idRancho);
            param.put("idUsuarioEditor", idUsuarioEditor);

            conn.update("ConsultaMedica.editarConsultaMedica", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Consulta médica editada correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        } finally {
            conn.close();
        }
        return res;
    }

    @POST
    @Path("cancelarConsultaMedica")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta cancelarConsultaMedica(
            @FormParam("idConsultaMedica") Integer idConsultaMedica,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idConsultaMedica", idConsultaMedica);
            param.put("idUsuarioEditor", idUsuarioEditor);

            conn.update("ConsultaMedica.cancelarConsultaMedica", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Consulta médica cancelada correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        } finally {
            conn.close();
        }
        return res;
    }
}
