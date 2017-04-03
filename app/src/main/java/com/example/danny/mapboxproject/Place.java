package com.example.danny.mapboxproject;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;


public class Place {

    String Name;
    String Image;
    LatLng Coord;
    String Descrip;

    public Place(){
        super();
    }

    public Place(String name, String image, LatLng coord, String descrip) {
        Name = name;
        Image = image;
        Coord = coord;
        Descrip = descrip;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public LatLng getCoord() {
        return Coord;
    }

    public void setCoord(LatLng coord) {
        Coord = coord;
    }

    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String descrip) {
        Descrip = descrip;
    }

    public List<Place> getPlacesList(){
        List<Place> list = new ArrayList<>();

        list.add(new Place("Igreja Monumento de São Francisco",
                "igejasaofrancisco", new LatLng(41.140950, -8.615674), "A Igreja de São Francisco é uma igreja gótica " +
                "da cidade do Porto, situada na freguesia de São Nicolau em pleno Centro histórico do Porto. " +
                "A construção iniciou-se no século XIV como parte de um convento Franciscano."));

        list.add(new Place("Monumento ao Infante Dom Henrique",
                "infantedomhenrique", new LatLng(41.141334, -8.614950),
                "Da autoria de Tomás Costa, a estátua foi realizada em 1894. Compõe-se de vários grupos escultóricos." +
                        " No cimo, a estátua do Infante, tendo junto, um globo terrestre. No sopé, vêem-se dois grupos " +
                        "alegóricos: uma Vitória conduzindo dois corcéis e dois tritões, representando o triunfo das " +
                        "navegações portuguesas; e uma figura feminina simbolizando a Fé das Descobertas. Possui, " +
                        "ainda, baixos-relevos junto ao pedestal, representando a tomada de Ceuta e o Infante no Promontório de Sagres."));

        list.add(new Place("Pelourinho do Porto", "pelourinhoporto",
                new LatLng(41.142477, -8.611961), "No seguimento da política nacional " +
                "implementada pelo Estado Novo, a antiga Direcção-Geral de Edifícios e Monumentos " +
                "Nacionais procedeu à demolição dos edifícios envolventes à Sé (Catedral), Casa do " +
                "Cabido e Paço Episcopal, tarefa terminada em 1940. Em sua substituição foi construído " +
                "um terreiro lajeado e para seu adorno a Câmara Municipal do Porto mandou executar um " +
                "pelourinho, reconstituição de uma gravura de 1797, procedendo à sua colocação em 1945."));

        list.add(new Place("Sé do Porto", "sedoporto", new LatLng(41.142737, -8.611346),
                "Construção do século XII/XIII, em estilo românico, foi sendo alvo de ampliações" +
                        " e renovações ao longo dos tempos até à sua alteração final, numa " +
                        "reconstituição idealizada da catedral medieval, já no século XX. " +
                        "Destaca-se: do século XIV, de estilo gótico, o claustro e a Capela de " +
                        "São João Evangelista; a ampliação da capela-mor, a Capela do Santíssimo " +
                        "Sacramento e seu altar de prata, do século XVII, ao estilo maneirista;" +
                        " do barroco, século XVIII, os frescos da capela-mor e a sacristia, da " +
                        "autoria de Nicolau Nasoni, bem como os azulejos do claustro, de Vital " +
                        "Rifarto; já do século XIX, a escultura de Teixeira Lopes (pai) na Capela Batismal."));

        list.add(new Place("Estátua de Vímara Peres", "vimaraperes", new LatLng(41.143133, -8.611147),
                "Vímara Peres (Reino das Astúrias, ca. 820 — Guimarães, 873) foi um senhor " +
                        "da guerra da segunda metade do século IX do noroeste da península Ibérica." +
                        " Nascido em Astúrias, vassalo de Afonso III, foi enviado a reclamar o vale " +
                        "do Douro, em tempos remotos integrado à província romana da Galécia."));

        list.add(new Place("Igreja da Misericórdia", "igrejamisericordia",
                new LatLng(41.143512, -8.614635), "A igreja começou a ser construída aproximadamente " +
                "em 1550 ao estilo renascentista. Na capela-mor, que apresenta, atualmente, um retábulo " +
                "neoclássico, foi utilizada a mesma solução utilizada no Mosteiro dos Jerónimos, mas " +
                "de dimensões mais reduzidas. Em 1740 a igreja ameaçava ruína, tendo sido os desenhos " +
                "de Nicolau Nasoni os escolhidos para a nova fachada do edifício, entre os de vários " +
                "especialistas. A fachada carateriza-se pelo virtuosismo da sua decoração. O interior " +
                "do templo, apesar do aditamento decorativo barroco, nomeadamente no arco triunfal e à" +
                " volta da cornija, mantém a austeridade que carateriza a arquitetura maneirista."));

        list.add(new Place("Monumento ao Duque da Ribeira", "duqueribeira", new LatLng(41.140781, -8.610179),
                "Deocleciano Monteiro, popularmente conhecido por Duque da Ribeira, (Porto, 24 de Março " +
                        "de 1902 – Porto, 9 de Novembro de 1996) foi um barqueiro e figura carismática da " +
                        "cidade do Porto, em Portugal."));

        return list;
    }
}