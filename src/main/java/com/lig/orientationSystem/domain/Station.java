package com.lig.orientationSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    //    还是建一个list存？
    //岗位的序号，和前端对应，从一开始
//    private Integer number;
    //岗位的描述
    private String name;
    //岗位的要求
    private String desc;

    //初始化岗位列表
    //前端、产品、后端、运营、UI
    public static ArrayList<Station> initStationList() {

        ArrayList<Station> arrayList = new ArrayList<>();
        arrayList.add(new Station("前端", "（以下为岗位介绍，具体要求可咨询面试官）\n" +
                "-前端核心模块的设计、开发，对代码质量及进度负责\n" +
                "-上承产品文档，下输测试原型，紧密配合后端对接，参与产品开发实战\n" +
                "-关注前沿技术，及时转化为生产\n" +
                "-技能：html&css&javascript、前端框架、算法等"));
        arrayList.add(new Station("产品", "（以下为岗位介绍，具体要求可咨询面试官）\n" +
                "-负责互联网产品的设计和推广，为产品的整个生命周期负责\n" +
                "-发现需求，解决需求，并用落地产品实现\n" +
                "-运用适当的工具完成各类产品文档，同时进行项目管理\n" +
                "技能：沟通、协作、洞察力、工具使用等"));
        arrayList.add(new Station("后端", "后端"));
        arrayList.add(new Station("运营", "（以下为岗位介绍，具体要求可咨询面试官）\n" +
                "-配合产品经理、前端，对程序&网页的后台设计负责\n" +
                "-维护数据库及系统的日常稳定\n" +
                "-技能：数据库、编程语言、算法等"));
        arrayList.add(new Station("UI", "（以下为岗位介绍，具体要求可咨询面试官）\n" +
                "- 负责产品视觉、交互设计，对产品的界面、操作逻辑负责\n" +
                "- 辅助产品分析需求，定位产品视觉元素，作为需求与具体实现的桥梁\n" +
                "- 熟练使用所需工具以表现设计，熟悉各平台设计规则，为用户着想的思维\n" +
                "- 熟练掌握与产品和前端对线能力\n" +
                "- 技能：Ps、Ai、Ae、Axure、审美、设计规范 "));
        return arrayList;
    }
}
