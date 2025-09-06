export const RadiologyType = {
  BREAST_ULTRASOUND: { id: -1, name: 'Breast Ultrasound' },
  MAMMOGRAPHY: { id: -2, name: 'Mammography' },
  TESTICULAR_ULTRASOUND: { id: -3, name: 'Testicular Ultrasound' },
  DOPPLER_ULTRASOUND_LOWER_LIMBS: { id: -4, name: 'Doppler Ultrasound Lower Limbs' },
  PITUITARY_MRI: { id: -5, name: 'Pituitary MRI' },
  PELVIC_SCAN: { id: -6, name: 'Pelvic Scan' },
  PELVIC_MRI: { id: -7, name: 'Pelvic MRI' },
  ABDOMINAL_ULTRASOUND: { id: -8, name: 'Abdominal Ultrasound' },
  ECHOCARDIOGRAPHY: { id: -9, name: 'Echocardiography' },
  SOFT_PART_ULTRASOUND: { id: -10, name: 'Soft Part Ultrasound' },
  RENAL_ULTRASOUND: { id: -11, name: 'Renal Ultrasound' },
  BRAIN_MRI: { id: -12, name: 'Brain MRI' },
  CHEST_MRI: { id: -13, name: 'Chest MRI' },
  CARDIAC_MRI: { id: -14, name: 'Cardiac MRI' },
  ABDOMINAL_MRI: { id: -15, name: 'Abdominal MRI' },
  BONE_MRI: { id: -16, name: 'Bone MRI' },
  BRAIN_SCAN: { id: -17, name: 'Brain Scan' },
  CHEST_SCAN: { id: -18, name: 'Chest Scan' },
  ABDOMINAL_SCAN: { id: -19, name: 'Abdominal Scan' },
  BONE_SCAN: { id: -20, name: 'Bone Scan' },
  LUNG_SCINTIGRAPHY: { id: -21, name: 'Lung Scintigraphy' }
} as const;

// Utility to get RadiologyType by ID
export function getRadiologyTypeById(id: number): typeof RadiologyType[keyof typeof RadiologyType] | undefined {
  return Object.values(RadiologyType).find(type => type.id === id);
}