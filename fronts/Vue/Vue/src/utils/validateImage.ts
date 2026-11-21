const IMAGE_TYPES  = ['image/jpeg', 'image/png', 'image/webp']

export function validateImage(file: File): string | null{
    if (!IMAGE_TYPES.includes(file.type)){
        return 'La imagen debe ser JPG, JPEG, PNG o WEBP'
    }
    if (file.size > 5 * 1024 * 1024){
        return 'La imagen no puede pesar más de 5 MB'
    }
    return null
}