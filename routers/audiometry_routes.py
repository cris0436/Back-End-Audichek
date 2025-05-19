from fastapi import APIRouter, Depends
from schemas.Audiometry import Audiometry as AudiometrySchema
from controllers.audiometry.add_audiometry import AddAudiometryController
from controllers.audiometry.imp.add_audiometry_imp import getAddAudiometryController
# Definir el router
router = APIRouter(prefix="/audiometries", tags=["Audiometries"])


@router.post("/", response_model=AudiometrySchema)
def create_audiometry(
    audiometry_data: AudiometrySchema,
    add_audiometry_controller: AddAudiometryController = Depends(getAddAudiometryController)):
    
    return add_audiometry_controller.add_audiometry(audiometry_data)
    