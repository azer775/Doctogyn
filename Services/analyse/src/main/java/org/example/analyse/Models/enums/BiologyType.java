package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BiologyType {
    OGTT_100G_1H_GLUCOSE_MMOL_L(-1, "100g OGTT 1h Glucose (mmol/L)"),
    OGTT_100G_1H_GLUCOSE_MG_DL(-2, "100g OGTT 1h Glucose (mg/dL)"),
    OGTT_100G_1H_GLUCOSE_G_L(-3, "100g OGTT 1h Glucose (g/L)"),
    OGTT_100G_2H_GLUCOSE_MMOL_L(-4, "100g OGTT 2h Glucose (mmol/L)"),
    OGTT_100G_2H_GLUCOSE_MG_DL(-5, "100g OGTT 2h Glucose (mg/dL)"),
    OGTT_100G_2H_GLUCOSE_G_L(-6, "100g OGTT 2h Glucose (g/L)"),
    OGTT_100G_FASTING_GLUCOSE_MMOL_L(-7, "100g OGTT Fasting Glucose (mmol/L)"),
    OGTT_100G_FASTING_GLUCOSE_MG_DL(-8, "100g OGTT Fasting Glucose (mg/dL)"),
    OGTT_100G_FASTING_GLUCOSE_G_L(-9, "100g OGTT Fasting Glucose (g/L)"),
    HYDROXYPROGESTERONE_MG_L(-10, "17-Hydroxyprogesterone (mg/L)"),
    HYDROXYPROGESTERONE_UMOL_L(-11, "17-Hydroxyprogesterone (μmol/L)"),
    OGTT_50G_1H_GLUCOSE_MG_DL(-12, "50g OGTT 1h Glucose (mg/dL)"),
    OGTT_50G_1H_GLUCOSE_G_L(-13, "50g OGTT 1h Glucose (g/L)"),
    OGTT_50G_1H_GLUCOSE_MMOL_L(-14, "50g OGTT 1h Glucose (mmol/L)"),
    OGTT_50G_FASTING_GLUCOSE_MMOL_L(-15, "50g OGTT Fasting Glucose (mmol/L)"),
    OGTT_50G_FASTING_GLUCOSE_MG_DL(-16, "50g OGTT Fasting Glucose (mg/dL)"),
    OGTT_50G_FASTING_GLUCOSE_G_L(-17, "50g OGTT Fasting Glucose (g/L)"),
    OGTT_75G_1H_GLUCOSE_MMOL_L(-18, "75g OGTT 1h Glucose (mmol/L)"),
    OGTT_75G_1H_GLUCOSE_MG_DL(-19, "75g OGTT 1h Glucose (mg/dL)"),
    OGTT_75G_1H_GLUCOSE_G_L(-20, "75g OGTT 1h Glucose (g/L)"),
    OGTT_75G_2H_GLUCOSE_MMOL_L(-21, "75g OGTT 2h Glucose (mmol/L)"),
    OGTT_75G_2H_GLUCOSE_MG_DL(-22, "75g OGTT 2h Glucose (mg/dL)"),
    OGTT_75G_2H_GLUCOSE_G_L(-23, "75g OGTT 2h Glucose (g/L)"),
    OGTT_75G_FASTING_GLUCOSE_MMOL_L(-24, "75g OGTT Fasting Glucose (mmol/L)"),
    OGTT_75G_FASTING_GLUCOSE_MG_DL(-25, "75g OGTT Fasting Glucose (mg/dL)"),
    OGTT_75G_FASTING_GLUCOSE_G_L(-26, "75g OGTT Fasting Glucose (g/L)"),
    ALANINE_AMINOTRANSFERASE_IU_L(-27, "Alanine Aminotransferase (IU/L)"),
    ALPHA_FETOPROTEIN_NG_ML(-28, "Alpha-Fetoprotein (ng/mL or μg/L)"),
    ANTI_MULLERIAN_HORMONE_PMOL_L(-29, "Anti-Müllerian Hormone (pmol/L)"),
    ANTI_MULLERIAN_HORMONE_NG_ML(-30, "Anti-Müllerian Hormone (ng/mL)"),
    ANTITHROMBIN_PERCENT(-31, "Antithrombin (%)"),
    ASPARTATE_AMINOTRANSFERASE_IU_L(-32, "Aspartate Aminotransferase (IU/L)"),
    BILIRUBIN_CONJUGATED_MG_DL(-33, "Bilirubin Conjugated/Direct (mg/dL)"),
    BILIRUBIN_CONJUGATED_UMOL_L(-34, "Bilirubin Conjugated/Direct (μmol/L)"),
    BILIRUBIN_TOTAL_MG_DL(-35, "Bilirubin Total (mg/dL)"),
    BILIRUBIN_TOTAL_UMOL_L(-36, "Bilirubin Total (μmol/L)"),
    CALCIUM_MMOL_L(-37, "Calcium (mmol/L)"),
    CHLORIDE_MMOL_L(-38, "Chloride (mmol/L or mEq/L)"),
    C_REACTIVE_PROTEIN_NMOL_L(-39, "C-Reactive Protein (nmol/L)"),
    C_REACTIVE_PROTEIN_MG_L(-40, "C-Reactive Protein (mg/L)"),
    CREATININE_UMOL_L(-41, "Creatinine (μmol/L)"),
    CREATININE_MG_DL(-42, "Creatinine (mg/dL)"),
    D_DIMERS_UG_L(-43, "D-Dimers (μg/L)"),
    DHEA_UG_DL(-44, "DHEA (μg/dL or pg/mL)"),
    DHEA_SULPHATE_UG_DL(-45, "DHEA Sulphate (μg/dL or pg/mL)"),
    FASTING_GLUCOSE_CONCENTRATION_MMOL_L(-46, "Fasting Glucose Concentration (mmol/L)"),
    FASTING_GLUCOSE_CONCENTRATION_MG_DL(-47, "Fasting Glucose Concentration (mg/dL)"),
    FASTING_GLUCOSE_CONCENTRATION_G_L(-48, "Fasting Glucose Concentration (g/L)"),
    FERRITIN_NG_ML(-49, "Ferritin (ng/mL or μg/L)"),
    FIBRINOGEN_G_L(-50, "Fibrinogen (g/L)"),
    FOLLICLE_STIMULATING_HORMONE_IU_L(-51, "Follicle Stimulating Hormone (IU/L)"),
    FREE_THYROXINE_PMOL_L(-52, "Free Thyroxine (pmol/L)"),
    FREE_THYROXINE_NG_DL(-53, "Free Thyroxine (ng/dL)"),
    GAMMA_GLUTAMYL_TRANSFERASE_U_L(-54, "Gamma-Glutamyl Transferase (U/L)"),
    GLYCATED_HAEMOGLOBIN_PERCENT(-55, "Glycated Haemoglobin (%)"),
    HDL_MMOL_L(-56, "HDL (mmol/L)"),
    HDL_MG_DL(-57, "HDL (mg/dL)"),
    HEMATOCRIT_PERCENT(-58, "Hematocrit (%)"),
    HEMOGLOBIN_G_L(-59, "Hemoglobin (g/L)"),
    HUMAN_CHORIONIC_GONADOTROPHIN_IU_L(-60, "Human Chorionic Gonadotrophin (IU/L or mU/mL)"),
    LDL_MMOL_L(-61, "LDL (mmol/L)"),
    LDL_MG_DL(-62, "LDL (mg/dL)"),
    LIPASE_U_L(-63, "Lipase (U/L)"),
    LUTEINISING_HORMONE_IU_L(-64, "Luteinising Hormone (IU/L)"),
    OESTRADIOL_PMOL_L(-65, "Oestradiol (pmol/L)"),
    OESTRADIOL_PG_ML(-66, "Oestradiol (pg/mL)"),
    PLATELET_PER_UL(-67, "Platelet (/μL)"),
    POTASSIUM_MMOL_L(-68, "Potassium (mmol/L or mEq/L)"),
    PROGESTERONE_NG_ML(-69, "Progesterone (ng/mL)"),
    PROGESTERONE_NMOL_L(-70, "Progesterone (nmol/L)"),
    PROLACTIN_UG_L(-71, "Prolactin (μg/L)"),
    PROLACTIN_MIU_L(-72, "Prolactin (mIU/L)"),
    PROTEIN_C_PERCENT(-73, "Protein C (%)"),
    PROTEIN_S_PERCENT(-74, "Protein S (%)"),
    PROTEINURIA_24H_MG_L(-75, "Proteinuria 24h (mg/L)"),
    PROTEINURIA_24H_MG_24H(-76, "Proteinuria 24h (mg/24h)"),
    PROTHROMBIN_TIME_PERCENT(-77, "Prothrombin Time (%)"),
    SODIUM_MMOL_L(-78, "Sodium (mmol/L or mEq/L)"),
    TESTOSTERONE_NMOL_L(-79, "Testosterone (nmol/L)"),
    TESTOSTERONE_NG_DL(-80, "Testosterone (ng/dL)"),
    THYROID_STIMULATING_HORMONE_MIU_L(-81, "Thyroid Stimulating Hormone (mIU/L or μIU/mL)"),
    TOTAL_CHOLESTEROL_MMOL_L(-82, "Total Cholesterol (mmol/L)"),
    TOTAL_CHOLESTEROL_MG_DL(-83, "Total Cholesterol (mg/dL)"),
    TRIGLYCERIDE_MMOL_L(-84, "Triglyceride (mmol/L)"),
    TRIGLYCERIDE_MG_DL(-85, "Triglyceride (mg/dL)"),
    UREA_MG_DL(-86, "Urea (mg/dL)"),
    UREA_MMOL_L(-87, "Urea (mmol/L)"),
    VITAMIN_D_NMOL_L(-88, "Vitamin D (nmol/L)"),
    VITAMIN_D_NG_ML(-89, "Vitamin D (ng/mL)"),
    WHITE_BLOOD_CELL_PER_MM3(-90, "White Blood Cell (/mm³ or /μL)"),
    URIC_ACID_UMOL_L(-91, "Uric Acid (μmol/L)"),
    URIC_ACID_MG_L(-92, "Uric Acid (mg/L)"),
    TCK_TCA(-93, "TCK/TCA"),
    B12_VITAMIN_PMOL_L(-94, "B12 Vitamin (pmol/L)"),
    B12_VITAMIN_NG_ML(-95, "B12 Vitamin (ng/mL)");

    private final int id;
    private final String name;
    private static final Map<Integer, BiologyType> BY_ID = new HashMap<>();

    static {
        for (BiologyType type : BiologyType.values()) {
            BY_ID.put(type.id, type);
        }
    }

    BiologyType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static BiologyType fromId(int id) {
        BiologyType type = BY_ID.get(id);
        if (type == null) {
            throw new IllegalArgumentException("No BiologyType with id " + id + " found");
        }
        return type;
    }
}
