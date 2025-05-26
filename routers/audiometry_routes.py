from fastapi import APIRouter, Depends
from schemas.Audiometry import Audiometry as AudiometrySchema
from controllers.audiometry.add_audiometry import AddAudiometryController
from controllers.audiometry.imp.add_audiometry_imp import getAddAudiometryController
from controllers.audiometry.get_audiometry import GetAudiometryController
from schemas.UserSchema import UserOut
from controllers.user.imp.auth_user_controller_imp import get_current_user
from controllers.audiometry.imp.get_audiometry_imp import get_audiometry_imp_controller

router = APIRouter(prefix="/audiometries", tags=["Audiometrías"])

@router.post("/", response_model=AudiometrySchema, summary="Registrar nueva audiometría")
def create_audiometry(
    audiometry_data: AudiometrySchema,
    add_audiometry_controller: AddAudiometryController = Depends(getAddAudiometryController)):
    """
    Registra una nueva audiometría con la lista de frecuencias, decibeles y oído al que corresponde (izquierdo o derecho).
    Los datos no se analizan automáticamente, solo se almacenan.
    """
    return add_audiometry_controller.add_audiometry(audiometry_data)
 
@router.get("/", summary="Obtener audiometrías del usuario actual" ,response_model=list)
def get_my_audiometries(
    get_audiometry_controller: GetAudiometryController = Depends(get_audiometry_imp_controller),
    current_user: UserOut = Depends(get_current_user)):
    """
    Retorna todas las audiometrías registradas por el usuario autenticado. Cada una incluye un análisis
    basado en los datos registrados (frecuencia, decibelios, oído, respuesta).
    """
    return get_audiometry_controller.getAudiometryByUserId(current_user.id)
