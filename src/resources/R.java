
package resources;

import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;

/**
 * Universidad del Valle
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class R {
  
  public static int H = 10, W = 10;
  public static int HGAP = 10;
  public static int VGAP = 10;
  
  public static String ERROR_SQL = "Error en la consulta a la Base de Datos\n\nDetalle del error: %s";
  public static String ERROR_SAVE_FAILS = "Error al guardar el elemento\n\n%s";
  public static String ERROR_LOAD_DATA_FAILS = "Error al cargar los datos\n\nDetalle del error: %s";
  public static String STR_ERROR = "Opps! Ocurrió un Error";
  public static String ERROR_ADD_FAILS = "Error al agregar los datos\n\n%s";
  public static String ERROR_REMOVE_FAILS = "Error al remover los datos\n\n%s";
  
  
  public static String APP_TITLE = "Gestion de MIO";
  public static String MN_FILE = "Archivo";
  public static String MN_EXIT = "Salir";
  public static String MN_HELP = "Ayuda";
  public static String MN_ABOUT = "Acerca de";
  public static String STR_ABOUT_INFO = "Proyecto realizado por Julian Andres Cantillo";
  
  public static String CMD_EXIT = "EXIT";
  public static String CMD_SHOW_ABOUT_DLG = "SHOW_ABOUT";
  public static String STR_NEW_BUS = "Nuevo Bus";
  public static String CMD_NEW_BUS = "NEW_BUS";
  public static String STR_BUS_DETAIL = "Detalle de Bus";
  public static String STR_CODE = "Placa";
  public static String STR_BUS_CAPACITY = "Capacidad";
  public static String STR_TYPE = "Tipo";
  public static String STR_BUS_MAKER = "Marca";
  public static String STR_BUS_MODEL = "Modelo";
  public static String STR_ACCEPT = "Aceptar";
  public static String STR_CANCEL = "Cancelar";
  public static String STR_SAVE = "Guardar";
  public static String CMD_CLOSE = "CMD_CLOSE";
  public static String CMD_SAVE = "CMD_SAVE_BUS";
  public static String CMD_ACCEPT = "CMD_ACCEPT";
  public static String STR_BUS = "Buses";
  public static String STR_LINES = "Rutas";
  public static String STR_USERS = "Usuarios";
  public static String STR_UPDATE_TABLES = "Actualizar";
  public static String CMD_UPDATE_TABLES = "CMD_UPDATE_TABLES";
  public static String STR_ID = "Identificador";
  public static String STR_ITEM_SAVED = "Elemento guardado exitosamente";
  public static String SRT_BUS_COLUMNS[] = {"ID", "Placa", "Capacidad", "Marca", "Modelo", "Tipo de Bus"};
  public static String STR_NAME = "Nombre";
  public static String STR_DESC = "Descripción";
  public static String STR_LINE_DETAIL = "Detalle de Ruta";
  public static String STR_NEW_LINE = "Nueva Ruta";
  public static String STR_LINE = "Ruta";
  public static String[] SRT_LINE_COLUMNS = { "ID", "Código", "Nombre", "Tipo", "Descripción" };
  public static String CMD_NEW_LINE = "CMD_NEW_LINE";
  public static String STR_IDENTIFICATION = "Identificación";
  public static String STR_FIRSTNAME = "Primer Nombre";
  public static String STR_LASTNAME = "Apellido";
  public static String STR_ADDRESS = "Dirección";
  public static String STR_CHARGE = "Carga";
  public static String STR_NEW_USER = "Nuevo Usuario";
  public static String CMD_NEW_USER = "CMD_NEW_USER";
  public static String[] SRT_USERS_COLUMNS = { "ID", "Identificación", "Nombre", "Apellido", "Dirección", "Carga", "Fecha de Creación"};
  public static String STR_RECHARGE = "Recargar";
  public static String CMD_RECHARGE = "CMD_RECHARGE";
  public static String MN_CREATE = "Crear";
  public static String STR_ADD_BUS = "Agregar Bus";
  public static String CMD_ADD_BUS = "CMD_ADD_BUS";
  public static String STR_REMOVE = "Remover Bus";
  public static String CMD_REMOVE_BUS = "CMD_REMOVE_BUS";
  public static String REMOVE_CONFIRM = "Confirma remover el elemento\n\n%s";
  public static String STR_SEARCH = "Buscar";
  public static String CMD_SEARCH = "CMD_SEARCH";
  public static String CMD_CANCEL_SEARCH = "CMD_CANCEL_SEARCH";
  public static String STR_NOT_FOUND = "No se encontró ninguna coincidencia con '%s'";
  public static String STR_USER_INFO = "Información del Usuario";
  public static String STR_UNCHARGE = "Descontar";
  public static String CMD_UNRECHARGE = "CMD_UNCHARGE";
  public static String STR_CHARGE_MOUNT = "Monto a cargar";
  public static String STR_ADD = "Agregar";
  public static String CMD_ADD_CHARGE = "CMD_ADD_CHARGE";
  public static String CMD_SEARCH_USERS = "CMD_SEARCH_USERS";
  public static String CMD_SEARCH_LINES = "CMD_SEARCH_LINES";
  public static String CMD_SEARCH_BUSES= "CMD_SEARCH_BUSES";
  public static String CMD_SEARCH_FORM = "CMD_SEARCH_FORM";
  public static String STR_SEARCH_BUSES = "Buscar Buses";
  public static String STR_SEARCH_LINES = "Buscar Rutas";
  public static String STR_SEARCH_USERS = "Buscar Usuarios";
  
  

}
