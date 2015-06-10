package avviait.controller;

import avviait.model.GiudizioFacade;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GiudizioDeleteController {
    @Inject
    private GiudizioFacade giudizioFacade;

    public void deleteGiudizio() {
        String idString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        Long id;
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

        try {
            id = Long.valueOf(idString);
            giudizioFacade.deleteGiudizio(id);

            flash.put("notification", "Giudizio cancellato con successo");
            flash.put("notificationType", "success");
        } catch (NumberFormatException e) {
            flash.put("notification", "Cancellazione giudizio fallita");
            flash.put("notificationType", "alert");
        }
    }
}
