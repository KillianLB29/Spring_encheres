package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.dal.CategorieDAO;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import fr.eni.tp.spring_encheres.exception.EnchereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("enchereService")
public class EnchereServiceImpl implements EnchereService {

    @Autowired
    private EnchereDAO enchereDAO;
    @Autowired
    private ArticleVenduDAO articleVenduDAO;
    @Autowired
    private UtilisateurDAO utilisateurDAO;

    @Override
    public List<Enchere> findByArticleId(long idArticle) {
        return enchereDAO.read(idArticle);
    }

    @Override
    public void enregistrerEnchere(Enchere enchere) {
        if(!utilisateurHasEnoughPoints(enchere))
        {
            System.out.println("L'utilisateur n'a pas assez de crédit pour enchérir!");
        }
        else
        {
            //Récupération de l'ancienne meilleur enchère pour récupérer l'utilisateur, lui rendre ses crédits car il n'est dorénavant plus la personne qui paye l'objet
            //On la modifie aprés qu'on soit sur que l'ajout de la nouvelle est fonctionné

            Enchere meilleurEnchere = meilleurEnchere(enchere.getArticleVendu().getNoArticle());
            if(meilleurEnchere.getUtilisateur().getNoUtilisateur()==enchere.getUtilisateur().getNoUtilisateur()){
                System.out.println("Vous etes déja la meilleur offre pour cet objet!");
            }
            else{
                List<Enchere> encheres = enchereDAO.read(enchere.getArticleVendu().getNoArticle());
                encheres = encheres.stream()
                        .filter(e -> e.getUtilisateur().getNoUtilisateur()==enchere.getUtilisateur().getNoUtilisateur())
                        .collect(Collectors.toList());
                if(encheres.size() > 0){ //On a déja une enchère en cours on va donc l'update
                    //Si on a déja une enchere en cours je la supprime et j'enregistre la nouvelle
                    enchereDAO.delete(enchere.getArticleVendu().getNoArticle(),enchere.getUtilisateur().getNoUtilisateur());
                    enchereDAO.save(enchere);

                }
                else{
                    //Sinon Enregistrement de la nouvelle meilleur enchère et substraction des points de l'utilisateur
                    enchereDAO.save(enchere);
                }
                //je soustrais en suite les points de l'utilisateur
                Utilisateur utilisateur = utilisateurDAO.read(enchere.getUtilisateur().getNoUtilisateur());
                utilisateur.setCredit(utilisateur.getCredit()-enchere.getMontantEnchere());
                utilisateurDAO.update(utilisateur);
                //On gère maintenant l'ancienne meilleur enchère
                //Je rend les crédits a l'utilisateur
                if(meilleurEnchere.getUtilisateur().getNoUtilisateur()>0){
                    Utilisateur ancienGagnant = utilisateurDAO.read(meilleurEnchere.getUtilisateur().getNoUtilisateur());
                    ancienGagnant.setCredit(ancienGagnant.getCredit()+meilleurEnchere.getMontantEnchere());
                    utilisateurDAO.update(ancienGagnant);
                }
                }
            }

        }

    @Override
    public void supprimerEnchere(long idArticle,long idUtilisateur) {
        enchereDAO.delete(idArticle,idUtilisateur);
    }

    @Override
    public List<Enchere> findAll() {
        return enchereDAO.findAll();
    }

    @Override
    public Enchere meilleurEnchere(long idArticle) {
        List<Enchere> encheres = enchereDAO.read(idArticle);
        if(encheres.size() == 0)
        {
            System.out.println("pas d'enchere");
            Utilisateur util = new Utilisateur();
            util.setPseudo("");
            Enchere enchere = new Enchere();
            enchere.setUtilisateur(util);
            return enchere;
        }
        else{
            Enchere meilleureEnchere = encheres.stream()
                    .max(Comparator.comparing(Enchere::getMontantEnchere))
                    .orElse(null);
            System.out.println("Enchere : "+meilleureEnchere);
            meilleureEnchere.setUtilisateur(utilisateurDAO.read(meilleureEnchere.getUtilisateur().getNoUtilisateur()));
            meilleureEnchere.setArticleVendu(articleVenduDAO.read(idArticle));
            System.out.println("meilleur enchere : "+meilleureEnchere);
            return meilleureEnchere;
        }
    }
    public boolean isEnchereValid(Enchere enchere, EnchereException enchereException) {
        boolean valid = true;

        return valid;
    }
    public boolean utilisateurHasEnoughPoints(Enchere enchere) {
        boolean valid = true;
        Utilisateur utilisateur = utilisateurDAO.read(enchere.getUtilisateur().getNoUtilisateur());
        if(utilisateur.getCredit()<enchere.getMontantEnchere())
        {
            valid = false;
        }
        return valid;
    }

//    @Override
//    public List<Enchere> findByUtilID(long idUtilisateur) {
//        return enchereDAO.readByUtilID(idUtilisateur);
//    }


}
